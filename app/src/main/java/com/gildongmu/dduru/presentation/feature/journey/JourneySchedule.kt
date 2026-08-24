package com.gildongmu.dduru.presentation.feature.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class JourneyScheduleDayUiState(
    val day: Int,
    val dateText: String,
)

data class JourneyScheduleItemUiState(
    val startTime: String? = null,
    val endTime: String? = null,
    val title: String,
    val location: String? = null,
    val memo: String? = null,
)

@Composable
fun JourneySchedule(
    days: List<JourneyScheduleDayUiState>,
    selectedDay: Int,
    dDayText: String,
    schedules: List<JourneyScheduleItemUiState>,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        JourneyScheduleHeader(
            days = days,
            selectedDay = selectedDay,
            dDayText = dDayText,
            onDayClick = onDayClick,
        )

        Spacer(modifier = Modifier.height(8.dp))

        schedules.forEachIndexed { index, schedule ->
            JourneyScheduleItem(
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                title = schedule.title,
                location = schedule.location,
                memo = schedule.memo,
            )
        }
    }
}

@Composable
private fun JourneyScheduleHeader(
    days: List<JourneyScheduleDayUiState>,
    selectedDay: Int,
    dDayText: String,
    onDayClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(days, key = JourneyScheduleDayUiState::day) { day ->
            val isSelected = day.day == selectedDay
            Row(
                modifier = Modifier
                    .background(
                        color = if (isSelected) DduruTheme.colors.secondary100 else DduruTheme.colors.gray200,
                        shape = RoundedCornerShape(99.dp),
                    )
                    .noRippleClickable { onDayClick(day.day) }
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp,
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${day.day}일차",
                    style = DduruTheme.typography.Caption1,
                    color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray700,
                )

                Text(
                    text = day.dateText,
                    style = DduruTheme.typography.Caption1,
                    color = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray700,
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .background(
                        color = DduruTheme.colors.gray100,
                        shape = RoundedCornerShape(99.dp),
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dDayText,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray700,
                )
            }
        }
    }
}

@Composable
fun JourneyScheduleItem(
    startTime: String?,
    endTime: String?,
    title: String,
    modifier: Modifier = Modifier,
    location: String? = null,
    memo: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .weight(40f)
                .fillMaxHeight(),
        ) {
            if (startTime == null && endTime == null) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray400,
                    modifier = Modifier
                        .width(12.dp)
                        .align(Alignment.Center),
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    startTime?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Caption1,
                            color = DduruTheme.colors.primary,
                            modifier = Modifier.padding((4.dp))
                        )

                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(48.dp)
                                .background(DduruTheme.colors.gray100)
                        )
                    }

                    endTime?.let {
                        Text(
                            text = it,
                            style = DduruTheme.typography.Caption1,
                            color = DduruTheme.colors.primary,
                            modifier = Modifier.padding((4.dp))
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(16f))

        Column(
            modifier = Modifier.weight(288f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = title,
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.gray900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            memo?.let {
                Text(
                    text = it,
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            location?.let {
                Text(
                    text = it,
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun JourneySchedulePreview() {
    DduruTheme {
        JourneySchedule(
            days = listOf(
                JourneyScheduleDayUiState(day = 1, dateText = "05.14"),
                JourneyScheduleDayUiState(day = 2, dateText = "05.15"),
                JourneyScheduleDayUiState(day = 3, dateText = "05.16"),
            ),
            selectedDay = 1,
            dDayText = "D-13",
            schedules = listOf(
                JourneyScheduleItemUiState(
                    startTime = "10:00",
                    endTime = "12:30",
                    title = "인천국제공항 출발",
                    location = "인천국제공항 제1여객터미널",
                    memo = "출발 2시간 전까지 도착",
                ),
                JourneyScheduleItemUiState(
                    startTime = "13:00",
                    title = "후쿠오카 공항 도착",
                    location = "후쿠오카 공항 국제선 터미널",
                ),
                JourneyScheduleItemUiState(
                    title = "호텔 체크인",
                    memo = "체크인 후 짐 맡기기",
                ),
            ),
            onDayClick = { },
            modifier = Modifier.padding(16.dp),
        )
    }
}
