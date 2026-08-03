package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.extension.parseDateTime
import com.gildongmu.dduru.core.common.extension.toRelativeTime
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ReceivedApplicationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatRequestViewModel @Inject constructor(
    private val participationRepository: ParticipationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ChatRequestUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ChatRequestUiState>> = _uiState.asStateFlow()

    init {
        loadParticipations()
    }

    fun onAcceptConfirm(participationId: String) {
        viewModelScope.launch {
            participationRepository.approveParticipation(participationId.toLong())
                .onSuccess { loadParticipations() }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onRejectConfirm(participationId: String) {
        viewModelScope.launch {
            participationRepository.rejectParticipation(participationId.toLong())
                .onSuccess { loadParticipations() }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    private fun loadParticipations() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            participationRepository.getParticipations()
                .onSuccess { list ->
                    val items = list.map { item ->
                        ChatRequestItemUiState(
                            id = item.participationId.toString(),
                            username = item.userName,
                            postTitle = item.postTitle,
                            time = item.appliedAt.parseDateTime().toRelativeTime(),
                            status = when (item.status) {
                                "CONTACTING" -> ReceivedApplicationStatus.CONTACTING
                                "APPROVED" -> ReceivedApplicationStatus.APPROVED
                                "REJECTED" -> ReceivedApplicationStatus.REJECTED
                                else -> ReceivedApplicationStatus.WAITING
                            }
                        )
                    }
                    val sections = buildList {
                        val contacting = items.filter { it.status == ReceivedApplicationStatus.CONTACTING }
                        val waiting = items.filter { it.status == ReceivedApplicationStatus.WAITING }
                        val done = items.filter {
                            it.status == ReceivedApplicationStatus.APPROVED ||
                                it.status == ReceivedApplicationStatus.REJECTED
                        }
                        if (contacting.isNotEmpty()) add(ChatRequestSectionUiState("연락중", contacting))
                        if (waiting.isNotEmpty()) add(ChatRequestSectionUiState("대기중", waiting))
                        if (done.isNotEmpty()) add(ChatRequestSectionUiState("완료", done))
                    }
                    _uiState.value = UiState.Success(ChatRequestUiState(sections = sections))
                }
                .onFailure {
                    _uiState.value = UiState.Failure
                }
        }
    }
}
