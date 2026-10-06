package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.common.extension.toKoreanDateString
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.DduruCalendar
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.TripDatePickerBox
import com.gildongmu.dduru.presentation.feature.mypage.model.MAX_PREFERRED_DATE_COUNT
import com.gildongmu.dduru.presentation.feature.mypage.model.PreferredDateRange
import java.time.LocalDate

@Composable
fun TravelDatePreferenceSection(
    dateRanges: List<PreferredDateRange>,
    onAddClick: () -> Unit,
    onRemoveClick: (Int) -> Unit,
    onDateSelected: (index: Int, date: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialEditingIndex: Int? = null
) {
    var editingIndex by remember { mutableStateOf(initialEditingIndex) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "선호 여행 날짜",
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(23.dp))

        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            dateRanges.forEachIndexed { index, dateRange ->
                PreferredDateRow(
                    order = index + 1,
                    dateRange = dateRange,
                    onRemoveClick = {
                        editingIndex = null
                        onRemoveClick(index)
                    },
                    onBoxClick = { editingIndex = index }
                )

                if (editingIndex == index) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DduruCalendar(
                        selectedStartDate = dateRange.startDate,
                        selectedEndDate = dateRange.endDate,
                        onDateSelected = { onDateSelected(index, it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (dateRanges.size < MAX_PREFERRED_DATE_COUNT) {
            Spacer(modifier = Modifier.height(if (dateRanges.isEmpty()) 0.dp else 9.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .noRippleClickable(onClick = onAddClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_gray),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray400,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "선호 날짜 추가",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400
                )
            }
        }
    }
}

@Composable
private fun PreferredDateRow(
    order: Int,
    dateRange: PreferredDateRange,
    onRemoveClick: () -> Unit,
    onBoxClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "선호날짜 $order",
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray500
            )

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color = DduruTheme.colors.gray200, shape = RoundedCornerShape(8.dp))
                    .noRippleClickable(onClick = onRemoveClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x_gray),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray600,
                    modifier = Modifier.size(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TripDatePickerBox(
                label = "시작 날짜",
                selectedDate = dateRange.startDate?.toKoreanDateString(),
                onClick = onBoxClick,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.width(8.dp)
            )
            TripDatePickerBox(
                label = "종료 날짜",
                selectedDate = dateRange.endDate?.toKoreanDateString(),
                onClick = onBoxClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true, name = "선호 여행 날짜 - 빈 상태")
@Composable
private fun TravelDatePreferenceSectionEmptyPreview() {
    DduruTheme {
        TravelDatePreferenceSection(
            dateRanges = emptyList(),
            onAddClick = {},
            onRemoveClick = {},
            onDateSelected = { _, _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "선호 여행 날짜 - 3개")
@Composable
private fun TravelDatePreferenceSectionFilledPreview() {
    DduruTheme {
        TravelDatePreferenceSection(
            dateRanges = listOf(
                PreferredDateRange(LocalDate.of(2025, 10, 6), LocalDate.of(2025, 10, 6)),
                PreferredDateRange(LocalDate.of(2025, 11, 12), LocalDate.of(2025, 11, 14)),
                PreferredDateRange()
            ),
            onAddClick = {},
            onRemoveClick = {},
            onDateSelected = { _, _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}
