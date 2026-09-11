package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.remote.websocket.StompChatClient
import com.gildongmu.dduru.domain.entity.chat.MessageEntity
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatRoomSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatListItem
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMessageUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.MessagePosition
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
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

private data class PendingSentMessage(val tempId: String, val content: String)

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val stompChatClient: StompChatClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState: StateFlow<ChatRoomUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatRoomSideEffect>()
    val sideEffect: SharedFlow<ChatRoomSideEffect> = _sideEffect.asSharedFlow()

    private var chatRoomId: Long = -1
    private var subscriptionJob: Job? = null
    private val pendingSentMessages = mutableListOf<PendingSentMessage>()
    private var isLoadingMore = false
    private var hasMoreMessages = true

    fun loadMessages(chatRoomId: Long) {
        this.chatRoomId = chatRoomId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
                .onSuccess { result ->
                    hasMoreMessages = result.hasNext
                    val roomInfo = result.roomInfo
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            roomType = roomInfo.roomType,
                            roomName = roomInfo.opponentNickname ?: roomInfo.journeyTitle ?: "",
                            postTitle = roomInfo.postTitle ?: "",
                            participantCount = roomInfo.memberCount ?: 0,
                            items = result.messages.toChatListItems()
                        )
                    }
                    result.messages.lastOrNull()?.let { lastMessage ->
                        markAsRead(chatRoomId, lastMessage.messageId)
                    }
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
                        it.copy(
                            isLoading = false,
                            errorMessage = userMessage,
                            isRetryable = isRetryable
                        )
                    }
                    if (code == "FORBIDDEN" || code == "NOT_FOUND") {
                        _sideEffect.emit(ChatRoomSideEffect.NavigateBack)
                    }
                }
        }
    }

    fun loadMoreMessages() {
        if (isLoadingMore || !hasMoreMessages) return
        val firstMessageId = _uiState.value.items
            .filterIsInstance<ChatListItem.Message>()
            .mapNotNull { it.message.id.toLongOrNull() }
            .minOrNull() ?: return

        isLoadingMore = true
        viewModelScope.launch {
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = firstMessageId)
                .onSuccess { result ->
                    hasMoreMessages = result.hasNext
                    if (result.messages.isNotEmpty()) {
                        val newItems = result.messages.toChatListItems()
                        _uiState.update { it.copy(items = newItems + it.items) }
                    }
                }
            isLoadingMore = false
        }
    }

    private fun startRoomSubscription(roomId: Long) {
        subscriptionJob?.cancel()
        subscriptionJob = viewModelScope.launch {
            while (isActive) {
                try {
                    stompChatClient.connectAndSubscribeToRoom(roomId).collect { event ->
                        when (event.eventType) {
                            "READ" -> {
                                val lastReadId = event.lastReadMessageId ?: return@collect
                                _uiState.update { state ->
                                    state.copy(
                                        items = state.items.map { item ->
                                            if (item is ChatListItem.Message) {
                                                val msgId = item.message.id.toLongOrNull()
                                                    ?: return@map item
                                                if (msgId <= lastReadId) {
                                                    item.copy(message = item.message.copy(unreadCount = 0))
                                                } else {
                                                    item
                                                }
                                            } else {
                                                item
                                            }
                                        }
                                    )
                                }
                            }
                            else -> {
                                val messageId = event.messageId?.toString() ?: return@collect
                                val createdAt = event.createdAt?.parseDateTime() ?: return@collect
                                val content = event.userMessage?.text ?: event.content ?: ""
                                val pendingSentMessage = pendingSentMessages.firstOrNull { it.content == content }
                                val isMySentMessage = pendingSentMessage != null
                                val newItem = ChatListItem.Message(
                                    ChatMessageUiState(
                                        id = messageId,
                                        senderId = event.sender?.id?.toString() ?: "",
                                        senderName = event.sender?.name ?: "",
                                        content = content,
                                        time = createdAt.format(TIME_FORMATTER),
                                        unreadCount = event.unreadCount ?: 0,
                                        isMine = event.isMine == true || isMySentMessage,
                                        position = MessagePosition.SINGLE
                                    )
                                )
                                if (isMySentMessage) pendingSentMessages.remove(pendingSentMessage)
                                _uiState.update { state ->
                                    val alreadyExists = state.items.any {
                                        it is ChatListItem.Message && it.message.id == messageId
                                    }
                                    if (alreadyExists) return@update state

                                    val newItems = if (isMySentMessage) {
                                        val tempIndex = state.items.indexOfFirst {
                                            it is ChatListItem.Message &&
                                                it.message.id == pendingSentMessage?.tempId
                                        }
                                        if (tempIndex >= 0) {
                                            state.items.toMutableList().also {
                                                it[tempIndex] = newItem.copy(
                                                    message = newItem.message.copy(unreadCount = 1)
                                                )
                                            }
                                        } else {
                                            (state.items + newItem).toMutableList()
                                        }
                                    } else {
                                        (state.items + newItem).toMutableList()
                                    }

                                    state.copy(items = recalculateTailPositions(newItems))
                                }
                                if (!isMySentMessage) {
                                    markAsRead(chatRoomId, messageId.toLong())
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    delay(500L)
                }
            }
        }
    }

    private fun recalculateTailPositions(items: MutableList<ChatListItem>): List<ChatListItem> {
        val msgIndices = items.indices.filter { items[it] is ChatListItem.Message }
        if (msgIndices.size < 2) return items
        val lastIdx = msgIndices.last()
        val prevIdx = msgIndices[msgIndices.size - 2]
        val lastMsg = (items[lastIdx] as ChatListItem.Message).message
        val prevMsg = (items[prevIdx] as ChatListItem.Message).message
        val sameGroup = lastMsg.senderId == prevMsg.senderId && lastMsg.time == prevMsg.time
        if (sameGroup) {
            val prevPos = prevMsg.position
            val newPrevPos = if (prevPos == MessagePosition.SINGLE || prevPos == MessagePosition.HEAD) {
                MessagePosition.HEAD
            } else {
                MessagePosition.MIDDLE
            }
            items[prevIdx] = (items[prevIdx] as ChatListItem.Message).let {
                it.copy(message = it.message.copy(position = newPrevPos))
            }
            items[lastIdx] = (items[lastIdx] as ChatListItem.Message).let {
                it.copy(message = it.message.copy(position = MessagePosition.TAIL))
            }
        }
        return items
    }

    fun markAsRead(chatRoomId: Long, lastReadMessageId: Long) {
        _uiState.update { state ->
            state.copy(
                items = state.items.map { item ->
                    if (item is ChatListItem.Message) {
                        val msgId = item.message.id.toLongOrNull() ?: return@map item
                        if (msgId <= lastReadMessageId && !item.message.isMine) {
                            item.copy(message = item.message.copy(unreadCount = 0))
                        } else {
                            item
                        }
                    } else {
                        item
                    }
                }
            )
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

    fun onBackClick() {
        viewModelScope.launch { _sideEffect.emit(ChatRoomSideEffect.NavigateBack) }
    }

    fun onMembersClick() {
        viewModelScope.launch { _sideEffect.emit(ChatRoomSideEffect.NavigateToMembers) }
    }

    fun onMenuClick() {
        viewModelScope.launch { _sideEffect.emit(ChatRoomSideEffect.NavigateToMenu) }
    }

    fun onPlusClick() = Unit

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onSendClick(text: String) {
        if (text.isBlank()) return
        _uiState.update { it.copy(inputText = "") }
        val tempId = "temp-${System.currentTimeMillis()}"
        pendingSentMessages.add(PendingSentMessage(tempId, text))
        val now = LocalDateTime.now()
        val optimisticItem = ChatListItem.Message(
            ChatMessageUiState(
                id = tempId,
                senderId = "",
                senderName = "",
                content = text,
                time = now.format(TIME_FORMATTER),
                unreadCount = 1,
                isMine = true,
                position = MessagePosition.SINGLE
            )
        )
        _uiState.update { state ->
            val todayLabel = now.format(DATE_DIVIDER_FORMATTER)
            val lastDateLabel = state.items
                .filterIsInstance<ChatListItem.DateDivider>()
                .lastOrNull()?.date
            val itemsToAdd = if (lastDateLabel != todayLabel) {
                listOf(ChatListItem.DateDivider(todayLabel), optimisticItem)
            } else {
                listOf(optimisticItem)
            }
            state.copy(items = state.items + itemsToAdd)
        }
        viewModelScope.launch {
            runCatching { stompChatClient.ensureConnectedAndSend(chatRoomId, text) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptionJob?.cancel()
        viewModelScope.launch {
            stompChatClient.disconnectRoom()
        }
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
            prev.sender?.userId == message.sender?.userId &&
            prev.createdAt.toLocalDate() == message.createdAt.toLocalDate() &&
            ChronoUnit.MINUTES.between(prev.createdAt, message.createdAt) < GROUPING_THRESHOLD_MINUTES

        val sameSenderAsNext = next != null &&
            next.sender?.userId == message.sender?.userId &&
            next.createdAt.toLocalDate() == message.createdAt.toLocalDate() &&
            ChronoUnit.MINUTES.between(message.createdAt, next.createdAt) < GROUPING_THRESHOLD_MINUTES

        val position = when {
            sameSenderAsPrev && sameSenderAsNext -> MessagePosition.MIDDLE
            sameSenderAsPrev -> MessagePosition.TAIL
            sameSenderAsNext -> MessagePosition.HEAD
            else -> MessagePosition.SINGLE
        }

        result.add(
            ChatListItem.Message(
                ChatMessageUiState(
                    id = message.messageId.toString(),
                    senderId = message.sender?.userId?.toString() ?: "",
                    senderName = message.sender?.nickname ?: "",
                    content = message.content ?: "",
                    time = message.createdAt.format(TIME_FORMATTER),
                    unreadCount = message.unreadCount ?: 0,
                    isMine = message.isMine,
                    position = position
                )
            )
        )
    }

    return result
}
