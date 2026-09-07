package com.gildongmu.dduru.presentation.feature.journey.state

import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyNewsItemUiState
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyScheduleDayUiState
import com.gildongmu.dduru.presentation.feature.journey.compoenet.JourneyScheduleItemUiState

data class JourneyUiState(
    val ongoingJourneys: List<OngoingJourneyUiState> = emptyList(),
    val todos: List<JourneyTodoUiState> = emptyList(),
    val scheduleDays: List<JourneyScheduleDayUiState> = emptyList(),
    val selectedScheduleDay: Int = 1,
    val scheduleDDayText: String = "",
    val schedules: List<JourneyScheduleItemUiState> = emptyList(),
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
    val newNewsCount: Int
)

data class JourneyTodoUiState(
    val id: Long,
    val title: String,
    val caption: String?,
    val assignees: List<String>,
    val isCompleted: Boolean
)

data class PastJourneyUiState(
    val id: Int,
    val title: String,
    val location: String,
    val date: String,
    val imageUrl: String?,
    val memberImageUrls: List<String>
)
