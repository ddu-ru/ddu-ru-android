package com.gildongmu.dduru.presentation.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ReceivedApplicationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
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
        }
    }

    fun onRejectConfirm(participationId: String) {
        viewModelScope.launch {
            participationRepository.rejectParticipation(participationId.toLong())
                .onSuccess { loadParticipations() }
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

    private fun String.parseDateTime(): LocalDateTime =
        try {
            ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        } catch (e: Exception) {
            LocalDateTime.parse(this)
        }

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
