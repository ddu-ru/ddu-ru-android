package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class DateSelectionTarget {
    START,
    END
}

data class DateSectionUiState(
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val selectionTarget: DateSelectionTarget = DateSelectionTarget.START
) {
    val startDate: String?
        get() = selectedStartDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    val endDate: String?
        get() = selectedEndDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    val isNextEnabled: Boolean
        get() = selectedStartDate != null && selectedEndDate != null
}
