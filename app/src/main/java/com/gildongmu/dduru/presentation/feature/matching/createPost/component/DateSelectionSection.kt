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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.toKoreanDateString
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiAction
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep

@Composable
fun DateSelectionSection(
    uiState: DateSectionUiState,
    onAction: (DateSectionUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CreatePostStepHeader(uiModel = MatchingCreatePostStep.DATE.headerUiModel)

        Spacer(modifier = Modifier.weight(16f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TripDatePickerBox(
                label = "출발 날짜",
                selectedDate = uiState.selectedStartDate.toKoreanDateString(),
                onClick = { onAction(DateSectionUiAction.StartDateClicked) },
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
                selectedDate = uiState.selectedEndDate.toKoreanDateString(),
                onClick = { onAction(DateSectionUiAction.EndDateClicked) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DduruCalendar(
            selectedStartDate = uiState.selectedStartDate,
            selectedEndDate = uiState.selectedEndDate,
            onDateSelected = { date -> onAction(DateSectionUiAction.DateSelected(date)) },
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
                uiState = DateSectionUiState(),
                onAction = {},
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 52.dp)
            )
        }
    }
}
