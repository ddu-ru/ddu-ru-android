package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

fun DateSectionUiState.reduce(action: DateSectionUiAction): DateSectionUiState {
    return when (action) {
        DateSectionUiAction.StartDateClicked -> {
            val resetState = resetRangeIfCompleted()
            resetState.copy(selectionTarget = DateSelectionTarget.START)
        }

        DateSectionUiAction.EndDateClicked -> {
            val resetState = resetRangeIfCompleted()
            resetState.copy(
                selectionTarget = DateSelectionTarget.END,
                selectedEndDate = if (resetState.selectedStartDate == null) null else resetState.selectedEndDate
            )
        }

        is DateSectionUiAction.DateSelected -> handleDateSelected(action.date)
    }
}

private fun DateSectionUiState.handleDateSelected(date: java.time.LocalDate): DateSectionUiState {
    return when (selectionTarget) {
        DateSelectionTarget.START -> {
            val updatedState = if (selectedEndDate != null && date.isAfter(selectedEndDate)) {
                copy(
                    selectedStartDate = selectedEndDate,
                    selectedEndDate = date
                )
            } else {
                copy(selectedStartDate = date)
            }

            if (updatedState.selectedEndDate == null) {
                updatedState.copy(selectionTarget = DateSelectionTarget.END)
            } else {
                updatedState
            }
        }

        DateSelectionTarget.END -> {
            when {
                selectedStartDate == null -> {
                    copy(
                        selectedStartDate = date,
                        selectionTarget = DateSelectionTarget.END
                    )
                }

                date.isBefore(selectedStartDate) -> {
                    copy(
                        selectedStartDate = date,
                        selectedEndDate = selectedStartDate
                    )
                }

                else -> {
                    copy(selectedEndDate = date)
                }
            }
        }
    }
}

private fun DateSectionUiState.resetRangeIfCompleted(): DateSectionUiState {
    return if (selectedStartDate != null && selectedEndDate != null) {
        copy(selectedEndDate = null)
    } else {
        this
    }
}
