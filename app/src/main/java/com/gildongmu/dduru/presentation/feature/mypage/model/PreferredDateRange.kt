package com.gildongmu.dduru.presentation.feature.mypage.model

import java.time.LocalDate

const val MAX_PREFERRED_DATE_COUNT = 3

data class PreferredDateRange(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
) {
    val isComplete: Boolean
        get() = startDate != null && endDate != null

    fun withSelectedDate(date: LocalDate): PreferredDateRange = when {
        startDate == null || isComplete -> PreferredDateRange(startDate = date)
        date.isBefore(startDate) -> PreferredDateRange(startDate = date, endDate = startDate)
        else -> copy(endDate = date)
    }
}
