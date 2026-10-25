package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.journey.entity.MyJourney
import com.gildongmu.dduru.domain.journey.entity.MyJourneyList
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.state.OngoingJourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.state.PastJourneyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<JourneyUiState>> = _uiState.asStateFlow()

    init {
        loadMyJourneys()
    }

    private fun loadMyJourneys() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            journeyRepository.getMyJourneys().fold(
                onSuccess = { journeys ->
                    Log.d(
                        TAG,
                        "나의 여정 조회 성공: 진행 중 ${journeys.activeJourneys.size}개, 완료 ${journeys.completedJourneys.size}개"
                    )
                    _uiState.value = UiState.Success(journeys.toUiState())
                },
                onFailure = { throwable ->
                    Log.e(TAG, "나의 여정 조회 실패", throwable)
                    _uiState.value = UiState.Failure
                }
            )
        }
    }

    fun onTodoCheckedChange(todoId: Long, isCompleted: Boolean) {
        val currentState = _uiState.value

        if (currentState is UiState.Success) {
            _uiState.value = UiState.Success(
                currentState.data.copy(
                    todos = currentState.data.todos.map { todo ->
                        if (todo.id == todoId) todo.copy(isCompleted = isCompleted) else todo
                    }
                )
            )
        }
    }

    fun onScheduleDayClick(day: Int) {
        val currentState = _uiState.value

        if (currentState is UiState.Success && currentState.data.scheduleDays.any { it.day == day }) {
            _uiState.value = UiState.Success(
                currentState.data.copy(selectedScheduleDay = day)
            )
        }
    }

    private companion object {
        const val TAG = "JourneyViewModel"
    }
}

private fun MyJourneyList.toUiState(): JourneyUiState = JourneyUiState(
    ongoingJourneys = activeJourneys.map(MyJourney::toOngoingJourneyUiState),
    pastJourneys = completedJourneys.map(MyJourney::toPastJourneyUiState)
)

private fun MyJourney.toOngoingJourneyUiState(): OngoingJourneyUiState = OngoingJourneyUiState(
    id = journeyId,
    imageUrl = photoUrl,
    dDay = startDDay,
    title = title,
    period = "$startDate - $endDate · $tripDurationText",
    tripDurationText = tripDurationText,
    memberCount = recruitCount,
    recruitCapacity = recruitCapacity,
    isOwner = isOwner,
    remainingTodoCount = 0,
    newNewsCount = 0
)

private fun MyJourney.toPastJourneyUiState(): PastJourneyUiState = PastJourneyUiState(
    id = journeyId,
    title = title,
    location = destination,
    date = "$startDate - $endDate · $tripDurationText",
    imageUrl = photoUrl,
    tripDurationText = tripDurationText,
    memberCount = recruitCount,
    recruitCapacity = recruitCapacity,
    isOwner = isOwner,
    memberImageUrls = emptyList()
)
