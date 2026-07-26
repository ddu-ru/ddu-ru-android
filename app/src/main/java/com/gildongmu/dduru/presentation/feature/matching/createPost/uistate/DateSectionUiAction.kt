package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import java.time.LocalDate

sealed interface DateSectionUiAction {
    data object StartDateClicked : DateSectionUiAction
    data object EndDateClicked : DateSectionUiAction
    data class DateSelected(val date: LocalDate) : DateSectionUiAction
}
