package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.chat.ChatRoomEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatMainSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMainUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatMainViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ChatMainUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ChatMainUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatMainSideEffect>()
    val sideEffect: SharedFlow<ChatMainSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadChatRooms()
    }

    fun onChatRoomClick(chatRoomId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(ChatMainSideEffect.NavigateToChatRoom(chatRoomId))
        }
    }

    fun loadMoreChatRooms() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (!current.hasNext || current.nextCursor == null) return
        viewModelScope.launch {
            chatRoomRepository.getChatRooms(cursor = current.nextCursor)
                .onSuccess { result ->
                    _uiState.value = UiState.Success(
                        current.copy(
                            chatRooms = current.chatRooms + result.chatRooms.map { it.toUiModel() },
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext
                        )
                    )
                }
        }
    }

    private fun loadChatRooms() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            chatRoomRepository.getChatRooms()
                .onSuccess { result ->
                    _uiState.value = UiState.Success(
                        ChatMainUiState(
                            chatRooms = result.chatRooms.map { it.toUiModel() },
                            nextCursor = result.nextCursor,
                            hasNext = result.hasNext
                        )
                    )
                }
                .onFailure {
                    _uiState.value = UiState.Failure
                }
        }
    }

    private fun ChatRoomEntity.toUiModel() = ChatRoomUiModel(
        id = chatRoomId.toString(),
        displayName = displayName,
        postTitle = postTitle,
        participantCount = if (roomType == ChatRoomType.GROUP) participantCount else null,
        lastMessagePreview = lastMessage?.content,
        lastMessageAt = lastMessage?.createdAt?.toRelativeTime() ?: "",
        unreadCount = unreadCount.toInt().coerceAtMost(99),
        roomType = roomType
    )

    private fun LocalDateTime.toRelativeTime(): String {
        val now = LocalDateTime.now()
        val minutes = maxOf(0, ChronoUnit.MINUTES.between(this, now))
        val hours = maxOf(0, ChronoUnit.HOURS.between(this, now))
        val days = maxOf(0, ChronoUnit.DAYS.between(this, now))
        return when {
            minutes < 1 -> "방금"
            hours < 1 -> "${minutes}분 전"
            days < 1 -> "${hours}시간 전"
            else -> "${days}일 전"
        }
    }
}
