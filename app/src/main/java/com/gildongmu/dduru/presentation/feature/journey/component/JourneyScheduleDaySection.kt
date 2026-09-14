package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleDay

@Composable
fun JourneyScheduleDaySection(
    days: List<JourneyScheduleDay>,
    selectedDay: Int?,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "날짜",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            days.forEach { item ->
                val selected = selectedDay == item.day

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .background(
                            if (selected) {
                                DduruTheme.colors.primary
                            } else {
                                DduruTheme.colors.gray100
                            }
                        )
                        .noRippleClickable {
                            onDayClick(item.day)
                        }
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.day}일차",
                        style = DduruTheme.typography.Caption1,
                        color = if (selected) {
                            DduruTheme.colors.gray0
                        } else {
                            DduruTheme.colors.gray700
                        }
                    )
                    Text(
                        text = item.date,
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.gray0.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleDaySectionPreview() = DduruTheme {
    JourneyScheduleDaySection(
        days = listOf(
            JourneyScheduleDay(1, "05.12"),
            JourneyScheduleDay(2, "05.13"),
            JourneyScheduleDay(3, "05.14")
        ),
        selectedDay = 2,
        onDayClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
