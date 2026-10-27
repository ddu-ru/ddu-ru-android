package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import com.gildongmu.dduru.presentation.feature.chat.component.ContentTab
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomMenuUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MENU_PHOTO_COUNT = 6
private const val NOT_READY_MESSAGE = "준비 중인 기능이에요."

@HiltViewModel
class ChatRoomMenuViewModel @Inject constructor(
    private val chatRoomRepository: ChatRoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatRoomMenuUiState())
    val uiState: StateFlow<ChatRoomMenuUiState> = _uiState.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun load(chatRoomId: Long) {
        viewModelScope.launch {
            chatRoomRepository.getMessages(chatRoomId, beforeMessageId = null)
                .onSuccess { result ->
                    val roomInfo = result.roomInfo
                    _uiState.update {
                        it.copy(
                            title = roomInfo.journeyTitle ?: roomInfo.opponentNickname ?: "",
                            participantCount = roomInfo.memberCount ?: 0,
                            photoUrls = result.messages
                                .flatMap { message -> message.images.map { image -> image.imageUrl } }
                                .takeLast(MENU_PHOTO_COUNT)
                                .reversed()
                        )
                    }
                }
        }
    }

    fun onContentTabSelected(tab: ContentTab) {
        _uiState.update { it.copy(selectedContentTab = tab) }
    }

    fun onLeaveClick() {
        _uiState.update { it.copy(isLeaveDialogVisible = true) }
    }

    fun onLeaveDismiss() {
        _uiState.update { it.copy(isLeaveDialogVisible = false) }
    }

    fun onLeaveConfirm() {
        _uiState.update { it.copy(isLeaveDialogVisible = false) }
        showNotReady()
    }

    fun showNotReady() {
        viewModelScope.launch { _message.emit(NOT_READY_MESSAGE) }
    }
}
