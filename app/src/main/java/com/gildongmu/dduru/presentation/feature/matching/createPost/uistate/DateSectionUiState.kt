package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import java.time.LocalDate

enum class DateSelectionTarget {
    START,
    END
}

data class DateSectionUiState(
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val selectionTarget: DateSelectionTarget = DateSelectionTarget.START
) {
    val isNextEnabled: Boolean
        get() = selectedStartDate != null && selectedEndDate != null
}
