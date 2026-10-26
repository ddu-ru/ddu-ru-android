package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.extension.parseDateTime
import com.gildongmu.dduru.core.common.extension.toRelativeTime
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomListEvent
import com.gildongmu.dduru.data.remote.dto.websocket.ChatRoomListEventRoom
import com.gildongmu.dduru.data.remote.websocket.StompChatClient
import com.gildongmu.dduru.domain.entity.chat.ChatRoomEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.domain.entity.chat.MessageType
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortOption
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatMainSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMainUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiModel
import com.gildongmu.dduru.presentation.feature.chat.util.lastMessagePreviewText
import com.gildongmu.dduru.presentation.feature.chat.util.toPreviewText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val EVENT_UPSERT = "UPSERT"
private const val EVENT_REMOVE = "REMOVE"
private const val INITIAL_RECONNECT_DELAY_MS = 1_000L
private const val MAX_RECONNECT_DELAY_MS = 30_000L

@HiltViewModel
class ChatMainViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository,
    private val stompChatClient: StompChatClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ChatMainUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ChatMainUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatMainSideEffect>()
    val sideEffect: SharedFlow<ChatMainSideEffect> = _sideEffect.asSharedFlow()

    private var selectedSort: ChatSortOption = ChatSortOption.ALL

    init {
        loadChatRooms()
        subscribeToRoomListUpdates()
    }

    fun onChatRoomClick(chatRoomId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(ChatMainSideEffect.NavigateToChatRoom(chatRoomId))
        }
    }

    fun onSortSelected(option: ChatSortOption) {
        if (option == selectedSort) return
        selectedSort = option
        loadChatRooms()
    }

    fun loadMoreChatRooms() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (!current.hasNext || current.nextCursor == null) return
        viewModelScope.launch {
            chatRoomRepository.getChatRooms(roomType = selectedSort.name, cursor = current.nextCursor)
                .onSuccess { result ->
                    val latest = (_uiState.value as? UiState.Success)?.data ?: current
                    val existingIds = latest.chatRooms.map { it.id }.toSet()
                    _uiState.value = UiState.Success(
                        latest.copy(
                            chatRooms = latest.chatRooms +
                                result.chatRooms.filter { it.chatRoomId !in existingIds }.map { it.toUiModel() },
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext
                        )
                    )
                }
        }
    }

    fun loadChatRooms() {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) _uiState.value = UiState.Loading
            chatRoomRepository.getChatRooms(roomType = selectedSort.name)
                .onSuccess { result ->
                    _uiState.value = UiState.Success(
                        ChatMainUiState(
                            chatRooms = result.chatRooms.map { it.toUiModel() }.sortedForList(),
                            selectedSort = selectedSort,
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext
                        )
                    )
                }
                .onFailure {
                    if (_uiState.value !is UiState.Success) _uiState.value = UiState.Failure
                }
        }
    }

    private fun subscribeToRoomListUpdates() {
        viewModelScope.launch {
            var retryDelay = INITIAL_RECONNECT_DELAY_MS
            while (isActive) {
                try {
                    stompChatClient.subscribeToRoomList().collect { event ->
                        retryDelay = INITIAL_RECONNECT_DELAY_MS
                        handleRoomListEvent(event)
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                }
                delay(retryDelay)
                retryDelay = (retryDelay * 2).coerceAtMost(MAX_RECONNECT_DELAY_MS)
                loadChatRooms()
            }
        }
    }

    private fun handleRoomListEvent(event: ChatRoomListEvent) {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        val payload = event.chatRoom

        val updatedRooms = when (event.eventType) {
            EVENT_REMOVE -> current.chatRooms.filterNot { it.id == payload.chatRoomId }
            EVENT_UPSERT -> {
                val payloadType = ChatRoomType.entries.firstOrNull { it.name == payload.roomType }
                val existing = current.chatRooms.firstOrNull { it.id == payload.chatRoomId }
                val roomType = payloadType ?: existing?.roomType
                if (roomType == null || !selectedSort.matches(roomType)) return

                val merged = existing?.mergeWith(payload) ?: payload.toUiModel(roomType)
                if (merged == null) {
                    loadChatRooms()
                    return
                }
                (current.chatRooms.filterNot { it.id == payload.chatRoomId } + merged).sortedForList()
            }
            else -> return
        }
        _uiState.value = UiState.Success(current.copy(chatRooms = updatedRooms))
    }

    private fun ChatRoomUiModel.mergeWith(payload: ChatRoomListEventRoom): ChatRoomUiModel {
        val lastMessage = payload.lastMessage
        return copy(
            displayName = payload.displayName ?: displayName,
            postTitle = payload.postTitle ?: postTitle,
            thumbnailUrl = payload.thumbnailUrl ?: thumbnailUrl,
            participantCount = if (roomType == ChatRoomType.GROUP) {
                payload.participantCount ?: participantCount
            } else {
                null
            },
            lastMessagePreview = lastMessage?.let {
                lastMessagePreviewText(messageTypeOf(it.messageType), it.content)
            } ?: lastMessagePreview,
            lastMessageAt = lastMessage?.createdAt?.parseDateTime()?.toRelativeTime() ?: lastMessageAt,
            unreadCount = payload.unreadCount?.toInt()?.coerceAtMost(99) ?: unreadCount,
            activityAt = payload.activityAt?.parseDateTime() ?: activityAt
        )
    }

    private fun ChatRoomListEventRoom.toUiModel(roomType: ChatRoomType): ChatRoomUiModel? {
        val name = displayName ?: return null
        return ChatRoomUiModel(
            id = chatRoomId,
            displayName = name,
            postTitle = postTitle,
            participantCount = if (roomType == ChatRoomType.GROUP) participantCount else null,
            lastMessagePreview = lastMessage?.let { lastMessagePreviewText(messageTypeOf(it.messageType), it.content) },
            lastMessageAt = lastMessage?.createdAt?.parseDateTime()?.toRelativeTime() ?: "",
            unreadCount = (unreadCount ?: 0L).toInt().coerceAtMost(99),
            roomType = roomType,
            thumbnailUrl = thumbnailUrl,
            activityAt = activityAt?.parseDateTime()
        )
    }

    private fun ChatRoomEntity.toUiModel() = ChatRoomUiModel(
        id = chatRoomId,
        displayName = displayName,
        postTitle = postTitle,
        participantCount = if (roomType == ChatRoomType.GROUP) participantCount else null,
        lastMessagePreview = lastMessage?.toPreviewText(),
        lastMessageAt = lastMessage?.createdAt?.toRelativeTime() ?: "",
        unreadCount = unreadCount.toInt().coerceAtMost(99),
        roomType = roomType,
        thumbnailUrl = thumbnailUrl,
        activityAt = activityAt ?: createdAt
    )

    private fun List<ChatRoomUiModel>.sortedForList(): List<ChatRoomUiModel> =
        sortedWith(compareByDescending<ChatRoomUiModel> { it.activityAt }.thenByDescending { it.id })

    private fun ChatSortOption.matches(roomType: ChatRoomType): Boolean = when (this) {
        ChatSortOption.ALL -> true
        ChatSortOption.PRIVATE -> roomType == ChatRoomType.PRIVATE
        ChatSortOption.GROUP -> roomType == ChatRoomType.GROUP
    }

    private fun messageTypeOf(value: String): MessageType =
        MessageType.entries.firstOrNull { it.name == value } ?: MessageType.TEXT
}
