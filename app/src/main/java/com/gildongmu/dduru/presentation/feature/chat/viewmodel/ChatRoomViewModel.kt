package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.entity.image.ChatImageUploader
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomEvent
import com.gildongmu.dduru.data.remote.dto.websocket.WebSocketSystemMessage
import com.gildongmu.dduru.data.remote.websocket.StompChatClient
import com.gildongmu.dduru.domain.entity.chat.ChatMessagesResultEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.domain.entity.chat.MessageEntity
import com.gildongmu.dduru.domain.entity.chat.MessageType
import com.gildongmu.dduru.domain.entity.chat.SystemMessageType
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.component.ChatAttachOption
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatRoomSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatListItem
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMessageKind
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMessageUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.MessagePosition
import com.gildongmu.dduru.presentation.feature.chat.util.displayText
import com.gildongmu.dduru.presentation.feature.chat.util.displayUserId
import com.gildongmu.dduru.presentation.feature.chat.util.systemMessageTypeOf
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private val DATE_DIVIDER_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
private val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private const val GROUPING_THRESHOLD_MINUTES = 5L
private const val MAX_MESSAGE_LENGTH = 4_000
private const val INITIAL_RECONNECT_DELAY_MS = 1_000L
private const val MAX_RECONNECT_DELAY_MS = 30_000L
private const val READ_EVENT_TYPE = "READ"

private data class PendingSentMessage(val tempId: String, val content: String)

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val stompChatClient: StompChatClient,
    private val chatImageUploader: ChatImageUploader
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState: StateFlow<ChatRoomUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatRoomSideEffect>()
    val sideEffect: SharedFlow<ChatRoomSideEffect> = _sideEffect.asSharedFlow()

    private var chatRoomId: Long = -1
    private var myUserId: Long? = null
    private val nicknames = mutableMapOf<Long, String>()
    private var subscriptionJob: Job? = null
    private val pendingSentMessages = mutableListOf<PendingSentMessage>()
    private var isLoadingMore = false
    private var hasMoreMessages = true
    private var nextCursor: Long? = null
    private var lastMarkedReadId: Long = 0
    private var pendingImageCount = 0

    fun loadMessages(chatRoomId: Long) {
        this.chatRoomId = chatRoomId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
                .onSuccess { result ->
                    applyLatestPage(result)
                    _uiState.update { it.copy(isLoading = false) }
                    startRoomSubscription(chatRoomId)
                }
                .onFailure { throwable ->
                    val code = throwable.message ?: "OTHER"
                    val (userMessage, isRetryable) = when (code) {
                        "FORBIDDEN" -> "접근할 수 없는 채팅방이에요." to false
                        "NOT_FOUND" -> "존재하지 않는 채팅방이에요." to false
                        "NETWORK_ERROR" -> "연결이 원활하지 않아요. 잠시 후 다시 시도해 주세요." to true
                        "SERVER_ERROR" -> "일시적인 오류가 발생했어요. 잠시 후 다시 시도해 주세요." to true
                        else -> "채팅방을 불러오지 못했어요. 다시 시도해 주세요." to true
                    }
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = userMessage, isRetryable = isRetryable)
                    }
                    if (code == "FORBIDDEN" || code == "NOT_FOUND") {
                        _sideEffect.emit(ChatRoomSideEffect.NavigateBack)
                    }
                }
        }
    }

    fun loadMoreMessages() {
        val cursor = nextCursor
        if (isLoadingMore || !hasMoreMessages || cursor == null) return

        isLoadingMore = true
        viewModelScope.launch {
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = cursor)
                .onSuccess { result ->
                    hasMoreMessages = result.hasNext
                    nextCursor = result.nextCursor
                    rememberSenders(result.messages)
                    if (result.messages.isNotEmpty()) {
                        val olderItems = result.messages.toChatListItems()
                        _uiState.update { it.copy(items = mergeOlderItems(olderItems, it.items)) }
                    }
                }
            isLoadingMore = false
        }
    }

    fun markAsRead(chatRoomId: Long, lastReadMessageId: Long) {
        if (lastReadMessageId <= lastMarkedReadId) return
        lastMarkedReadId = lastReadMessageId

        if (_uiState.value.roomType == ChatRoomType.PRIVATE) {
            _uiState.update { state ->
                state.copy(
                    items = state.items.map { item ->
                        val messageId = (item as? ChatListItem.Message)?.message?.id?.toLongOrNull()
                        if (item is ChatListItem.Message && !item.message.isMine &&
                            messageId != null && messageId <= lastReadMessageId
                        ) {
                            item.copy(message = item.message.copy(unreadCount = 0))
                        } else {
                            item
                        }
                    }
                )
            }
        }
        viewModelScope.launch {
            chatRoomRepository.markAsRead(chatRoomId, lastReadMessageId)
        }
    }

    fun cancelSubscription() {
        subscriptionJob?.cancel()
        subscriptionJob = null
    }

    fun retryLoadMessages() {
        _uiState.update { it.copy(errorMessage = null, isRetryable = false) }
        loadMessages(chatRoomId)
    }

    fun onBackClick() = emitEffect(ChatRoomSideEffect.NavigateBack)

    fun onMembersClick() = emitEffect(ChatRoomSideEffect.NavigateToMembers)

    fun onMenuClick() = emitEffect(ChatRoomSideEffect.NavigateToMenu)

    fun onPlusClick() {
        _uiState.update { it.copy(isAttachPanelOpen = !it.isAttachPanelOpen) }
    }

    fun onAttachOptionClick(option: ChatAttachOption) {
        when (option) {
            ChatAttachOption.PHOTO -> {
                _uiState.update { it.copy(isAttachPanelOpen = false) }
                emitEffect(ChatRoomSideEffect.OpenPhotoPicker)
            }
            ChatAttachOption.POLL -> {
                _uiState.update { it.copy(isAttachPanelOpen = false) }
                emitEffect(ChatRoomSideEffect.NavigateToPollCreate)
            }
            ChatAttachOption.CAMERA,
            ChatAttachOption.MAP -> emitEffect(ChatRoomSideEffect.ShowMessage("준비 중인 기능이에요."))
        }
    }

    fun onPhotosSelected(uris: List<Uri>) {
        if (uris.isEmpty() || !_uiState.value.isInputEnabled) return
        viewModelScope.launch {
            _uiState.update { it.copy(isUploadingImage = true) }
            chatImageUploader.uploadImages(uris)
                .onSuccess { fileUrls ->
                    pendingImageCount += fileUrls.size
                    val failedCount = fileUrls.count { fileUrl ->
                        !stompChatClient.sendMessage(chatRoomId, MessageType.IMAGE.name, fileUrl)
                    }
                    pendingImageCount = (pendingImageCount - failedCount).coerceAtLeast(0)
                    if (failedCount > 0) {
                        _sideEffect.emit(ChatRoomSideEffect.ShowMessage("사진을 보내지 못했어요. 잠시 후 다시 시도해 주세요."))
                    }
                }
                .onFailure { throwable ->
                    _sideEffect.emit(ChatRoomSideEffect.ShowMessage(throwable.message ?: "사진을 보내지 못했어요."))
                }
            _uiState.update { it.copy(isUploadingImage = false) }
        }
    }

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onSendClick(text: String) {
        val content = text.trim()
        if (content.isEmpty() || !_uiState.value.isInputEnabled) return
        if (content.length > MAX_MESSAGE_LENGTH) {
            emitEffect(ChatRoomSideEffect.ShowMessage("메시지는 4,000자까지 보낼 수 있어요."))
            return
        }

        val tempId = "temp-${System.currentTimeMillis()}"
        pendingSentMessages.add(PendingSentMessage(tempId, content))
        val now = LocalDateTime.now()
        val optimisticMessage = ChatMessageUiState(
            id = tempId,
            senderId = myUserId?.toString().orEmpty(),
            senderName = "",
            content = content,
            time = now.format(TIME_FORMATTER),
            unreadCount = expectedUnreadCountForMine(),
            isMine = true,
            position = MessagePosition.SINGLE
        )
        _uiState.update { state ->
            val items = state.items.toMutableList()
            addDateDividerIfNeeded(items, now)
            items.add(ChatListItem.Message(optimisticMessage))
            state.copy(inputText = "", items = recalculateTailPositions(items))
        }

        val isSent = stompChatClient.sendMessage(chatRoomId, MessageType.TEXT.name, content)
        if (!isSent) {
            pendingSentMessages.removeAll { it.tempId == tempId }
            _uiState.update { state ->
                state.copy(
                    inputText = content,
                    items = state.items.filterNot { it is ChatListItem.Message && it.message.id == tempId }
                )
            }
            emitEffect(ChatRoomSideEffect.ShowMessage("메시지를 보내지 못했어요. 잠시 후 다시 시도해 주세요."))
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionJob?.cancel()
    }

    private fun applyLatestPage(result: ChatMessagesResultEntity) {
        hasMoreMessages = result.hasNext
        nextCursor = result.nextCursor
        rememberSenders(result.messages)
        val roomInfo = result.roomInfo
        _uiState.update {
            it.copy(
                roomType = roomInfo.roomType,
                roomName = roomInfo.opponentNickname ?: roomInfo.journeyTitle ?: "",
                postTitle = roomInfo.postTitle ?: "",
                participantCount = roomInfo.memberCount ?: 0,
                isInputEnabled = roomInfo.isActive,
                items = result.messages.toChatListItems()
            )
        }
        result.messages.lastOrNull()?.let { markAsRead(chatRoomId, it.messageId) }
    }

    private fun rememberSenders(messages: List<MessageEntity>) {
        messages.forEach { message ->
            val sender = message.sender ?: return@forEach
            nicknames[sender.userId] = sender.nickname
            if (message.isMine) myUserId = sender.userId
        }
    }

    private fun startRoomSubscription(roomId: Long) {
        subscriptionJob?.cancel()
        subscriptionJob = viewModelScope.launch {
            var retryDelay = INITIAL_RECONNECT_DELAY_MS
            var isReconnect = false
            while (isActive) {
                if (isReconnect) resyncLatestMessages()
                try {
                    stompChatClient.connectAndSubscribeToRoom(roomId).collect { event ->
                        retryDelay = INITIAL_RECONNECT_DELAY_MS
                        handleRoomEvent(event)
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                }
                isReconnect = true
                delay(retryDelay)
                retryDelay = (retryDelay * 2).coerceAtMost(MAX_RECONNECT_DELAY_MS)
            }
        }
    }

    private suspend fun resyncLatestMessages() {
        chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
            .onSuccess { applyLatestPage(it) }
    }

    private suspend fun refreshUnreadCounts() {
        chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
            .onSuccess { result ->
                val counts = result.messages.associate { it.messageId.toString() to (it.unreadCount ?: 0) }
                _uiState.update { state ->
                    state.copy(
                        items = state.items.map { item ->
                            if (item is ChatListItem.Message) {
                                counts[item.message.id]?.let { count ->
                                    item.copy(message = item.message.copy(unreadCount = count))
                                } ?: item
                            } else {
                                item
                            }
                        }
                    )
                }
            }
    }

    private fun handleRoomEvent(event: ChatRoomEvent) {
        if (event.eventType == READ_EVENT_TYPE) {
            handleReadEvent(event)
            return
        }
        val messageId = event.messageId ?: return
        val createdAt = event.createdAt?.parseDateTime() ?: return
        when (event.messageType) {
            MessageType.SYSTEM.name -> appendSystemMessage(messageId, createdAt, event.systemMessage)
            MessageType.TEXT.name, MessageType.IMAGE.name -> appendUserMessage(event, messageId, createdAt)
        }
    }

    private fun handleReadEvent(event: ChatRoomEvent) {
        val readerUserId = event.readerUserId ?: return
        val lastReadId = event.lastReadMessageId ?: return
        if (readerUserId == myUserId) return

        if (_uiState.value.roomType == ChatRoomType.GROUP) {
            viewModelScope.launch { refreshUnreadCounts() }
            return
        }
        _uiState.update { state ->
            state.copy(
                items = state.items.map { item ->
                    val messageId = (item as? ChatListItem.Message)?.message?.id?.toLongOrNull()
                    if (item is ChatListItem.Message && item.message.isMine &&
                        messageId != null && messageId <= lastReadId
                    ) {
                        item.copy(message = item.message.copy(unreadCount = 0))
                    } else {
                        item
                    }
                }
            )
        }
    }

    private fun appendUserMessage(event: ChatRoomEvent, messageId: Long, createdAt: LocalDateTime) {
        val sender = event.sender
        sender?.name?.let { nicknames[sender.id] = it }

        val isImage = event.messageType == MessageType.IMAGE.name
        val text = event.userMessage?.text.orEmpty()
        val imageUrl = event.userMessage?.imageUrl
        if (isImage && imageUrl == null) return

        val pending = if (isImage) null else pendingSentMessages.firstOrNull { it.content == text }
        val isPendingImage = isImage && myUserId == null && pendingImageCount > 0
        if (myUserId == null && sender != null && (pending != null || isPendingImage)) myUserId = sender.id
        val isMine = (sender != null && sender.id == myUserId) || pending != null
        if (isImage && isMine && pendingImageCount > 0) pendingImageCount--

        val message = ChatMessageUiState(
            id = messageId.toString(),
            senderId = sender?.id?.toString().orEmpty(),
            senderName = sender?.name.orEmpty(),
            content = text,
            time = createdAt.format(TIME_FORMATTER),
            unreadCount = if (isMine) expectedUnreadCountForMine() else expectedUnreadCountForOthers(),
            isMine = isMine,
            position = MessagePosition.SINGLE,
            kind = if (isImage) ChatMessageKind.IMAGE else ChatMessageKind.TEXT,
            imageUrls = listOfNotNull(imageUrl)
        )
        pending?.let { pendingSentMessages.remove(it) }
        appendOrReplace(message, createdAt, replaceId = pending?.tempId)

        if (!isMine) markAsRead(chatRoomId, messageId)
    }

    private fun appendSystemMessage(
        messageId: Long,
        createdAt: LocalDateTime,
        payload: WebSocketSystemMessage?
    ) {
        val type = systemMessageTypeOf(payload?.type) ?: return
        val displayUserId = type.displayUserId(
            inviteeUserId = payload?.inviteeUserId,
            userId = payload?.userId,
            targetUserId = payload?.targetUserId
        )
        val message = ChatMessageUiState(
            id = messageId.toString(),
            senderId = "",
            senderName = "",
            content = type.displayText(displayUserId?.let { nicknames[it] }),
            time = createdAt.format(TIME_FORMATTER),
            unreadCount = 0,
            isMine = false,
            position = MessagePosition.SINGLE,
            kind = ChatMessageKind.SYSTEM
        )
        appendOrReplace(message, createdAt, replaceId = null)

        if (type == SystemMessageType.ROOM_CLOSED) {
            _uiState.update { it.copy(isInputEnabled = false, isAttachPanelOpen = false) }
        }
        markAsRead(chatRoomId, messageId)
        viewModelScope.launch { resyncLatestMessages() }
    }

    private fun appendOrReplace(message: ChatMessageUiState, createdAt: LocalDateTime, replaceId: String?) {
        _uiState.update { state ->
            val alreadyExists = state.items.any { it is ChatListItem.Message && it.message.id == message.id }
            if (alreadyExists) return@update state

            val items = state.items.toMutableList()
            val replaceIndex = replaceId?.let { id ->
                items.indexOfFirst { it is ChatListItem.Message && it.message.id == id }
            } ?: -1

            if (replaceIndex >= 0) {
                items[replaceIndex] = ChatListItem.Message(message)
            } else {
                addDateDividerIfNeeded(items, createdAt)
                items.add(ChatListItem.Message(message))
            }
            state.copy(items = recalculateTailPositions(items))
        }
    }

    private fun addDateDividerIfNeeded(items: MutableList<ChatListItem>, dateTime: LocalDateTime) {
        val label = dateTime.format(DATE_DIVIDER_FORMATTER)
        val lastLabel = items.filterIsInstance<ChatListItem.DateDivider>().lastOrNull()?.date
        if (lastLabel != label) items.add(ChatListItem.DateDivider(label))
    }

    private fun mergeOlderItems(older: List<ChatListItem>, current: List<ChatListItem>): List<ChatListItem> {
        val lastOlderDate = older.filterIsInstance<ChatListItem.DateDivider>().lastOrNull()?.date
        val firstCurrent = current.firstOrNull()
        val trimmedCurrent = if (firstCurrent is ChatListItem.DateDivider && firstCurrent.date == lastOlderDate) {
            current.drop(1)
        } else {
            current
        }
        return older + trimmedCurrent
    }

    private fun expectedUnreadCountForMine(): Int = when (_uiState.value.roomType) {
        ChatRoomType.PRIVATE -> 1
        ChatRoomType.GROUP -> (_uiState.value.participantCount - 1).coerceAtLeast(0)
    }

    private fun expectedUnreadCountForOthers(): Int = when (_uiState.value.roomType) {
        ChatRoomType.PRIVATE -> 0
        ChatRoomType.GROUP -> (_uiState.value.participantCount - 2).coerceAtLeast(0)
    }

    private fun recalculateTailPositions(items: MutableList<ChatListItem>): List<ChatListItem> {
        val msgIndices = items.indices.filter { items[it] is ChatListItem.Message }
        if (msgIndices.size < 2) return items
        val lastIdx = msgIndices.last()
        val prevIdx = msgIndices[msgIndices.size - 2]
        val lastMsg = (items[lastIdx] as ChatListItem.Message).message
        val prevMsg = (items[prevIdx] as ChatListItem.Message).message
        val sameGroup = lastMsg.kind != ChatMessageKind.SYSTEM &&
            prevMsg.kind != ChatMessageKind.SYSTEM &&
            lastMsg.senderId == prevMsg.senderId &&
            lastMsg.time == prevMsg.time
        if (sameGroup) {
            val newPrevPos = if (prevMsg.position == MessagePosition.SINGLE ||
                prevMsg.position == MessagePosition.HEAD
            ) {
                MessagePosition.HEAD
            } else {
                MessagePosition.MIDDLE
            }
            items[prevIdx] = ChatListItem.Message(prevMsg.copy(position = newPrevPos))
            items[lastIdx] = ChatListItem.Message(lastMsg.copy(position = MessagePosition.TAIL))
        }
        return items
    }

    private fun emitEffect(effect: ChatRoomSideEffect) {
        viewModelScope.launch { _sideEffect.emit(effect) }
    }

    private fun String.parseDateTime(): LocalDateTime =
        try {
            ZonedDateTime.parse(this)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()
        } catch (e: Exception) {
            LocalDateTime.parse(this)
        }
}

private fun List<MessageEntity>.toChatListItems(): List<ChatListItem> {
    val result = mutableListOf<ChatListItem>()
    var lastDate: String? = null

    forEachIndexed { index, message ->
        val dateLabel = message.createdAt.format(DATE_DIVIDER_FORMATTER)
        if (dateLabel != lastDate) {
            result.add(ChatListItem.DateDivider(dateLabel))
            lastDate = dateLabel
        }

        val prev = getOrNull(index - 1)
        val next = getOrNull(index + 1)

        val sameSenderAsPrev = prev != null &&
            prev.messageType != MessageType.SYSTEM &&
            prev.sender?.userId == message.sender?.userId &&
            prev.createdAt.toLocalDate() == message.createdAt.toLocalDate() &&
            ChronoUnit.MINUTES.between(prev.createdAt, message.createdAt) < GROUPING_THRESHOLD_MINUTES

        val sameSenderAsNext = next != null &&
            next.messageType != MessageType.SYSTEM &&
            next.sender?.userId == message.sender?.userId &&
            next.createdAt.toLocalDate() == message.createdAt.toLocalDate() &&
            ChronoUnit.MINUTES.between(message.createdAt, next.createdAt) < GROUPING_THRESHOLD_MINUTES

        val position = when {
            message.messageType == MessageType.SYSTEM -> MessagePosition.SINGLE
            sameSenderAsPrev && sameSenderAsNext -> MessagePosition.MIDDLE
            sameSenderAsPrev -> MessagePosition.TAIL
            sameSenderAsNext -> MessagePosition.HEAD
            else -> MessagePosition.SINGLE
        }

        val kind = when (message.messageType) {
            MessageType.TEXT -> ChatMessageKind.TEXT
            MessageType.IMAGE -> ChatMessageKind.IMAGE
            MessageType.SYSTEM -> ChatMessageKind.SYSTEM
        }

        result.add(
            ChatListItem.Message(
                ChatMessageUiState(
                    id = message.messageId.toString(),
                    senderId = message.sender?.userId?.toString() ?: "",
                    senderName = message.sender?.nickname ?: "",
                    content = if (kind == ChatMessageKind.SYSTEM) {
                        message.systemMessage?.displayText.orEmpty()
                    } else {
                        message.content.orEmpty()
                    },
                    time = message.createdAt.format(TIME_FORMATTER),
                    unreadCount = message.unreadCount ?: 0,
                    isMine = message.isMine,
                    position = position,
                    kind = kind,
                    imageUrls = message.images.map { it.imageUrl }
                )
            )
        )
    }

    return result
}
