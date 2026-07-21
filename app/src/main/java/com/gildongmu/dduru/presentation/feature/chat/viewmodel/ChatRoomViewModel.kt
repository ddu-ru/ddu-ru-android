package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.domain.entity.chat.MessageEntity
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatListItem
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMessageUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.MessagePosition
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val DATE_DIVIDER_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
private val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

sealed interface ChatRoomUiEvent {
    data object NavigateBack : ChatRoomUiEvent
    data object NavigateToMembers : ChatRoomUiEvent
    data object NavigateToMenu : ChatRoomUiEvent
}

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState: StateFlow<ChatRoomUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ChatRoomUiEvent>()
    val uiEvent: SharedFlow<ChatRoomUiEvent> = _uiEvent.asSharedFlow()

    fun loadMessages(chatRoomId: Long) {
        viewModelScope.launch {
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
                .onSuccess { result ->
                    val roomInfo = result.roomInfo
                    _uiState.update {
                        it.copy(
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
                }
                .onFailure { throwable ->
                    when {
                        throwable.message?.contains("400") == true -> emitUiEvent(ChatRoomUiEvent.NavigateBack)
                        throwable.message?.contains("403") == true -> emitUiEvent(ChatRoomUiEvent.NavigateBack)
                        throwable.message?.contains("404") == true -> emitUiEvent(ChatRoomUiEvent.NavigateBack)
                        else -> Unit
                    }
                }
        }
    }

    fun markAsRead(chatRoomId: Long, lastReadMessageId: Long) {
        viewModelScope.launch {
            chatRoomRepository.markAsRead(chatRoomId, lastReadMessageId)
        }
    }

    fun onBackClick() {
        emitUiEvent(ChatRoomUiEvent.NavigateBack)
    }

    fun onMembersClick() {
        emitUiEvent(ChatRoomUiEvent.NavigateToMembers)
    }

    fun onMenuClick() {
        emitUiEvent(ChatRoomUiEvent.NavigateToMenu)
    }

    fun onPlusClick() = Unit

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onSendClick(text: String) {
        _uiState.update { it.copy(inputText = "") }
    }

    private fun emitUiEvent(event: ChatRoomUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
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
            prev.createdAt.toLocalDate() == message.createdAt.toLocalDate()
        val sameSenderAsNext = next != null &&
            next.sender?.userId == message.sender?.userId &&
            next.createdAt.toLocalDate() == message.createdAt.toLocalDate()

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
