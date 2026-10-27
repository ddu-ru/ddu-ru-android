package com.gildongmu.dduru.presentation.feature.journey.state

import com.gildongmu.dduru.presentation.feature.journey.component.JourneyNewsItemUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleDayUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyScheduleItemUiState

data class JourneyUiState(
    val ongoingJourneys: List<OngoingJourneyUiState> = emptyList(),
    val selectedJourneyId: Long? = null,
    val todos: List<JourneyTodoUiState> = emptyList(),
    val scheduleDays: List<JourneyScheduleDayUiState> = emptyList(),
    val selectedScheduleDay: Int = 1,
    val scheduleDDayText: String = "",
    val schedules: List<JourneyScheduleItemUiState> = emptyList(),
    val schedulesByDay: Map<Int, List<JourneyScheduleItemUiState>> = emptyMap(),
    val news: List<JourneyNewsItemUiState> = emptyList(),
    val pastJourneys: List<PastJourneyUiState> = emptyList()
)

data class OngoingJourneyUiState(
    val id: Long,
    val imageUrl: String?,
    val dDay: String,
    val title: String,
    val period: String,
    val memberCount: Int,
    val remainingTodoCount: Int,
    val newNewsCount: Int,
    val tripDurationText: String = "",
    val recruitCapacity: Int = 0,
    val isOwner: Boolean = false
)

data class JourneyTodoUiState(
    val id: Long,
    val title: String,
    val caption: String?,
    val assignees: List<String>,
    val isCompleted: Boolean
)

data class PastJourneyUiState(
    val id: Long,
    val title: String,
    val location: String,
    val date: String,
    val imageUrl: String?,
    val memberImageUrls: List<String>,
    val tripDurationText: String = "",
    val memberCount: Int = 0,
    val recruitCapacity: Int = 0,
    val isOwner: Boolean = false
)
