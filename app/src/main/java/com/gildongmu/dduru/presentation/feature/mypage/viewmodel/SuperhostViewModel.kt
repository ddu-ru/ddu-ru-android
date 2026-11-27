package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.SuperHostPost
import com.gildongmu.dduru.domain.entity.mypage.SuperHostStatus
import com.gildongmu.dduru.domain.repository.SuperHostRepository
import com.gildongmu.dduru.presentation.component.DduruToastMessage
import com.gildongmu.dduru.presentation.component.toErrorToast
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetMode
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetStep
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetUiState
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostUiState
import com.gildongmu.dduru.presentation.feature.mypage.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SUPER_HOST_PERIOD_DAYS = 3L
private const val SUPER_HOST_PERIOD_TEXT = "3일 (72시간)"
private val SuperHostEndDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy년 M월 d일(E) HH:mm", Locale.KOREAN)

@HiltViewModel
class SuperhostViewModel @Inject constructor(
    private val superHostRepository: SuperHostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<SuperhostUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<SuperhostUiState>> = _uiState.asStateFlow()

    private val _errorToast = MutableSharedFlow<DduruToastMessage>()
    val errorToast: SharedFlow<DduruToastMessage> = _errorToast.asSharedFlow()

    private var status: SuperHostStatus? = null
    private var loadJob: Job? = null

    fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (_uiState.value !is UiState.Success) _uiState.value = UiState.Loading
            superHostRepository.getMyStatus()
                .onSuccess { result ->
                    status = result
                    val now = LocalDateTime.now()
                    val sheet = (_uiState.value as? UiState.Success)?.data?.sheet
                    _uiState.value = UiState.Success(
                        SuperhostUiState(
                            ticketCount = result.unusedTicketCount,
                            appliedPosts = result.appliedPosts.map { it.toUiModel(now) },
                            unappliedPosts = result.availablePosts.map { it.toUiModel(now) },
                            sheet = sheet
                        )
                    )
                }
                .onFailure { error ->
                    if (_uiState.value is UiState.Success) {
                        _errorToast.emit(error.toErrorToast())
                    } else {
                        _uiState.value = UiState.Failure
                    }
                }
        }
    }

    fun onApplyClick(postId: Long) {
        val post = status?.availablePosts?.firstOrNull { it.postId == postId } ?: return
        openSheet(post, SuperhostSheetMode.APPLY, LocalDateTime.now())
    }

    fun onExtendClick(postId: Long) {
        val post = status?.appliedPosts?.firstOrNull { it.postId == postId } ?: return
        openSheet(post, SuperhostSheetMode.EXTEND, post.superHostEndsAt ?: LocalDateTime.now())
    }

    fun onSheetConfirm() {
        val sheet = currentSheet() ?: return
        if (sheet.step != SuperhostSheetStep.CONFIRM) return
        updateSheet { it?.copy(step = SuperhostSheetStep.LOADING) }

        viewModelScope.launch {
            superHostRepository.applyTicket(sheet.post.id)
                .onSuccess { result ->
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: return@update state
                        UiState.Success(data.copy(ticketCount = result.unusedTicketCount))
                    }
                    updateSheet { current ->
                        current?.copy(
                            step = SuperhostSheetStep.COMPLETE,
                            endDate = result.endsAt?.format(SuperHostEndDateFormatter) ?: current.endDate
                        )
                    }
                }
                .onFailure { error ->
                    updateSheet { null }
                    _errorToast.emit(error.toErrorToast())
                }
        }
    }

    fun onSheetDismiss() {
        val sheet = currentSheet() ?: return
        if (sheet.step == SuperhostSheetStep.LOADING) return
        updateSheet { null }
        if (sheet.step == SuperhostSheetStep.COMPLETE) load()
    }

    private fun openSheet(post: SuperHostPost, mode: SuperhostSheetMode, startAt: LocalDateTime) {
        val data = (_uiState.value as? UiState.Success)?.data ?: return
        if (data.ticketCount <= 0) return
        updateSheet {
            SuperhostSheetUiState(
                post = post.toUiModel(LocalDateTime.now()),
                mode = mode,
                period = SUPER_HOST_PERIOD_TEXT,
                endDate = startAt.plusDays(SUPER_HOST_PERIOD_DAYS).format(SuperHostEndDateFormatter),
                ticketCount = data.ticketCount
            )
        }
    }

    private fun currentSheet(): SuperhostSheetUiState? = (_uiState.value as? UiState.Success)?.data?.sheet

    private fun updateSheet(transform: (SuperhostSheetUiState?) -> SuperhostSheetUiState?) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(data.copy(sheet = transform(data.sheet)))
        }
    }
}
