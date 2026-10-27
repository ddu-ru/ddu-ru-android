package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.extension.parseDateTime
import com.gildongmu.dduru.core.common.extension.toRelativeTime
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleDay
import com.gildongmu.dduru.domain.journey.entity.MyJourney
import com.gildongmu.dduru.domain.journey.entity.MyJourneyList
import com.gildongmu.dduru.domain.journey.repository.JourneyPostRepository
import com.gildongmu.dduru.domain.journey.repository.JourneyRepository
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyNewsItemUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleDayUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleItemUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.state.OngoingJourneyUiState
import com.gildongmu.dduru.presentation.feature.journey.state.PastJourneyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val journeyRepository: JourneyRepository,
    private val journeyPostRepository: JourneyPostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<JourneyUiState>> = _uiState.asStateFlow()
    private var isLoading = false

    init {
        refreshMyJourneys()
    }

    fun refreshMyJourneys() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val hasCurrentData = _uiState.value is UiState.Success
            if (!hasCurrentData) _uiState.value = UiState.Loading
            journeyRepository.getMyJourneys().fold(
                onSuccess = { journeys ->
                    Log.d(
                        TAG,
                        "나의 여정 조회 성공: 진행 중 ${journeys.activeJourneys.size}개, 완료 ${journeys.completedJourneys.size}개"
                    )
                    val state = journeys.toUiState()
                    val firstJourney = state.ongoingJourneys.firstOrNull()
                    _uiState.value = UiState.Success(state.copy(selectedJourneyId = firstJourney?.id))
                    firstJourney?.let(::loadJourneyOverview)
                },
                onFailure = { throwable ->
                    Log.e(TAG, "나의 여정 조회 실패", throwable)
                    if (!hasCurrentData) _uiState.value = UiState.Failure
                }
            )
            isLoading = false
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
                    .let { data ->
                        data.copy(schedules = data.schedulesByDay[day].orEmpty())
                    }
            )
        }
    }

    fun onJourneySelected(journeyId: Long) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        if (currentState.selectedJourneyId == journeyId) return
        val journey = currentState.ongoingJourneys.find { it.id == journeyId } ?: return
        _uiState.value = UiState.Success(currentState.copy(selectedJourneyId = journeyId))
        loadJourneyOverview(journey)
    }

    private fun loadJourneyOverview(journey: OngoingJourneyUiState) {
        viewModelScope.launch {
            val (scheduleResult, postResult) = coroutineScope {
                val schedules = async { journeyRepository.getJourneySchedules(journey.id) }
                val posts = async { journeyPostRepository.getJourneyPosts(journey.id, size = 3) }
                schedules.await() to posts.await()
            }
            val currentState = (_uiState.value as? UiState.Success)?.data ?: return@launch
            if (currentState.selectedJourneyId != journey.id) return@launch

            val scheduleList = scheduleResult.getOrNull()
            val news = postResult.getOrNull()?.posts.orEmpty().take(3).map {
                JourneyNewsItemUiState(
                    id = it.journeyPostId,
                    title = it.content,
                    journeyTitle = journey.title,
                    timeText = it.createdAt.parseDateTime().toRelativeTime(),
                    isUnread = false
                )
            }
            val selectedDay = scheduleList?.days?.firstOrNull()?.day ?: 1
            val schedulesByDay = scheduleList?.days.orEmpty().associate { day ->
                day.day to day.schedules.map { it.toUiState() }
            }
            _uiState.value = UiState.Success(
                currentState.copy(
                    selectedJourneyId = journey.id,
                    scheduleDays = scheduleList?.days.orEmpty().map(JourneyScheduleDay::toUiState),
                    selectedScheduleDay = selectedDay,
                    scheduleDDayText = journey.dDay,
                    schedules = schedulesByDay[selectedDay].orEmpty(),
                    schedulesByDay = schedulesByDay,
                    news = news,
                    ongoingJourneys = currentState.ongoingJourneys.map {
                        if (it.id == journey.id) it.copy(newNewsCount = news.size) else it
                    }
                )
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

private fun JourneyScheduleDay.toUiState() = JourneyScheduleDayUiState(
    day = day,
    dateText = date.toMonthDay()
)

private fun com.gildongmu.dduru.domain.journey.entity.JourneySchedule.toUiState() = JourneyScheduleItemUiState(
    startTime = startTime?.toHourMinute(),
    endTime = endTime?.toHourMinute(),
    title = title,
    location = placeName,
    memo = memo
)

private fun String.toMonthDay(): String = split("-")
    .takeLast(2)
    .joinToString(".")

private fun String.toHourMinute(): String = take(5)
