package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.model.StepHeaderUiModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSelectionTarget
import com.gildongmu.dduru.presentation.feature.matching.creative.component.DduruCalendar
import com.gildongmu.dduru.presentation.feature.matching.creative.component.TripDatePickerBox
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelectionSection(
    modifier: Modifier = Modifier
) {
    val stepHeaderDate = remember {
        StepHeaderUiModel(
            step = 2,
            title = "언제 떠나시나요?",
            subtitle = "날짜를 선택해 주세요."
        )
    }
    var uiState by remember { mutableStateOf(DateSectionUiState()) }

    fun formatDate(date: LocalDate?): String? {
        if (date == null) return null

        return date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
    }

    fun handleDateSelected(date: LocalDate) {
        when (uiState.selectionTarget) {
            DateSelectionTarget.START -> {
                if (uiState.selectedEndDate != null && date.isAfter(uiState.selectedEndDate)) {
                    uiState = uiState.copy(
                        selectedStartDate = uiState.selectedEndDate,
                        selectedEndDate = date
                    )
                } else {
                    uiState = uiState.copy(selectedStartDate = date)
                }

                if (uiState.selectedEndDate == null) {
                    uiState = uiState.copy(selectionTarget = DateSelectionTarget.END)
                }
            }

            DateSelectionTarget.END -> {
                if (uiState.selectedStartDate == null) {
                    uiState = uiState.copy(
                        selectedStartDate = date,
                        selectionTarget = DateSelectionTarget.END
                    )
                    return
                }

                if (date.isBefore(uiState.selectedStartDate)) {
                    uiState = uiState.copy(
                        selectedStartDate = date,
                        selectedEndDate = uiState.selectedStartDate
                    )
                } else {
                    uiState = uiState.copy(selectedEndDate = date)
                }
            }
        }
    }

    fun selectStartDateField() {
        uiState = uiState.copy(
            selectionTarget = DateSelectionTarget.START,
            selectedEndDate = if (uiState.selectedStartDate != null && uiState.selectedEndDate != null) {
                null
            } else {
                uiState.selectedEndDate
            }
        )
    }

    fun selectEndDateField() {
        uiState = uiState.copy(
            selectionTarget = DateSelectionTarget.END,
            selectedEndDate = if (uiState.selectedStartDate == null) {
                null
            } else {
                uiState.selectedEndDate
            }
        )
    }

    fun resetRangeIfCompleted() {
        if (uiState.selectedStartDate != null && uiState.selectedEndDate != null) {
            uiState = uiState.copy(selectedEndDate = null)
        }
    }

    fun handleStartBoxClick() {
        resetRangeIfCompleted()
        selectStartDateField()
    }

    fun handleEndBoxClick() {
        resetRangeIfCompleted()
        selectEndDateField()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        CreatePostStepHeader(uiModel = stepHeaderDate)

        Spacer(modifier = Modifier.weight(16f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripDatePickerBox(
                label = "출발 날짜",
                selectedDate = formatDate(uiState.selectedStartDate),
                onClick = ::handleStartBoxClick,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.width(width = 8.dp)
            )

            TripDatePickerBox(
                label = "도착 날짜",
                selectedDate = formatDate(uiState.selectedEndDate),
                onClick = ::handleEndBoxClick,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DduruCalendar(
            selectedStartDate = uiState.selectedStartDate,
            selectedEndDate = uiState.selectedEndDate,
            onDateSelected = ::handleDateSelected,
            modifier = Modifier
                .fillMaxWidth()
                .weight(389f)
        )

        Spacer(modifier = Modifier.weight(23f))
    }
}

@Preview(showBackground = true)
@Composable
private fun DateSelectionSectionPreview() {
    DduruTheme {
        Surface(color = DduruTheme.colors.gray0) {
            DateSelectionSection(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp)
            )
        }
    }
}
