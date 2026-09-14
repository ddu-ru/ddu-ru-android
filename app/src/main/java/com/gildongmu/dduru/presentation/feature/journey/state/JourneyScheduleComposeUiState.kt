package com.gildongmu.dduru.presentation.feature.journey.state

data class JourneyScheduleComposeUiState(
    val title: String = "",
    val location: String = "",
    val days: List<JourneyScheduleDay> = emptyList(),
    val selectedDay: Int? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val isEndTimeAdded: Boolean = false,
    val isMemoAdded: Boolean = false,
    val memo: String = "",
    val isCategoryAdded: Boolean = false,
    val selectedCategory: JourneyScheduleCategory? = null
) {
    val canSubmit: Boolean
        get() = title.isNotBlank() && selectedDay != null
}

data class JourneyScheduleDay(
    val day: Int,
    val date: String
)

enum class JourneyScheduleCategory(
    val label: String,
    val emoji: String
) {
    FOOD("식사", "🍚"),
    CAFE("카페", "☕"),
    TOUR("관광", "✈️"),
    ACTIVITY("액티비티", "⛰️"),
    SHOPPING("쇼핑", "🛍️"),
    REST("휴식", "👏")
}
