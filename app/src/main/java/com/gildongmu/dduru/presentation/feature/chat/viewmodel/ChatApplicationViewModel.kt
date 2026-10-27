package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.extension.parseDateTime
import com.gildongmu.dduru.core.common.extension.toRelativeTime
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatApplicationSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.SentApplicationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatApplicationViewModel @Inject constructor(
    private val participationRepository: ParticipationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ChatApplicationUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ChatApplicationUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChatApplicationSideEffect>()
    val sideEffect: SharedFlow<ChatApplicationSideEffect> = _sideEffect.asSharedFlow()

    private var roomIdCache: Map<String, Long> = emptyMap()

    init {
        loadMyParticipations()
    }

    fun onCancelClick(participationId: String) {
        viewModelScope.launch {
            participationRepository.cancelParticipation(participationId.toLong())
                .onSuccess { loadMyParticipations() }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onEnterClick(participationId: String) {
        val roomId = roomIdCache[participationId] ?: return
        viewModelScope.launch {
            _sideEffect.emit(ChatApplicationSideEffect.NavigateToChatRoom(roomId))
        }
    }

    private fun loadMyParticipations() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            participationRepository.getMyParticipations()
                .onSuccess { list ->
                    roomIdCache = list.associate { item ->
                        val roomId = item.privateRoomId ?: item.groupRoomId
                        item.participationId.toString() to (roomId ?: 0L)
                    }.filterValues { it != 0L }

                    val uiItems = list.map { item ->
                        ChatApplicationItemUiState(
                            id = item.participationId.toString(),
                            username = "",
                            postTitle = item.postTitle,
                            time = item.appliedAt.parseDateTime().toRelativeTime(),
                            status = when (item.status) {
                                "CONTACTING" -> SentApplicationStatus.CONTACTING
                                "APPROVED" -> SentApplicationStatus.APPROVED
                                "REJECTED", "REMOVED_BY_HOST" -> SentApplicationStatus.REJECTED
                                else -> SentApplicationStatus.WAITING
                            }
                        )
                    }
                    val sections = buildList {
                        val pending = uiItems.filter {
                            it.status == SentApplicationStatus.CONTACTING ||
                                it.status == SentApplicationStatus.WAITING
                        }
                        val done = uiItems.filter {
                            it.status == SentApplicationStatus.APPROVED ||
                                it.status == SentApplicationStatus.REJECTED
                        }
                        if (pending.isNotEmpty()) add(ChatApplicationSectionUiState("대기중", pending))
                        if (done.isNotEmpty()) add(ChatApplicationSectionUiState("확정됨", done))
                    }
                    _uiState.value = UiState.Success(ChatApplicationUiState(sections = sections))
                }
                .onFailure {
                    _uiState.value = UiState.Failure
                }
        }
    }
}
