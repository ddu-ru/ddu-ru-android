package com.gildongmu.dduru.presentation.feature.tripgroup.createPost.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@Composable
fun DduruCalendar(
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentYearMonth by remember {
        mutableStateOf(
            if (selectedStartDate != null) {
                YearMonth.from(selectedStartDate)
            } else {
                YearMonth.now()
            }
        )
    }

    val normalizedStartDate =
        if (selectedStartDate != null && selectedEndDate != null && selectedStartDate.isAfter(selectedEndDate)) {
            selectedEndDate
        } else {
            selectedStartDate
        }
    val normalizedEndDate =
        if (selectedStartDate != null && selectedEndDate != null && selectedStartDate.isAfter(selectedEndDate)) {
            selectedStartDate
        } else {
            selectedEndDate
        }

    val hasRange = normalizedStartDate != null && normalizedEndDate != null
    val statusText = if (hasRange) {
        val nights = ChronoUnit.DAYS.between(normalizedStartDate, normalizedEndDate).toInt()
        "총 ${nights}박 ${nights + 1}일"
    } else {
        "날짜를 선택하세요"
    }
    val statusColor = if (hasRange) DduruTheme.colors.primary else DduruTheme.colors.gray400

    Column(
        modifier = modifier
            .border(width = 1.dp, color = DduruTheme.colors.gray300, shape = RoundedCornerShape(16.dp))
            .background(color = DduruTheme.colors.gray0, shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    tint = DduruTheme.colors.black,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable { currentYearMonth = currentYearMonth.minusMonths(1) }
                )
                Text(
                    text = "${currentYearMonth.year}년 ${currentYearMonth.monthValue}월",
                    style = DduruTheme.typography.Title2,
                    color = DduruTheme.colors.black
                )
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = DduruTheme.colors.black,
                    modifier = Modifier
                        .size(16.dp)
                        .noRippleClickable { currentYearMonth = currentYearMonth.plusMonths(1) }
                )
            }
            Text(
                text = statusText,
                style = DduruTheme.typography.Title3,
                color = statusColor,
                textAlign = TextAlign.End
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("월", "화", "수", "목", "금", "토", "일").forEach { label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = DduruTheme.typography.Caption2,
                            color = DduruTheme.colors.gray700,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            val firstDay = currentYearMonth.atDay(1)
            val startOffset = (firstDay.dayOfWeek.value - 1) % 7
            val daysInMonth = currentYearMonth.lengthOfMonth()
            val prevMonth = currentYearMonth.minusMonths(1)
            val nextMonth = currentYearMonth.plusMonths(1)
            val daysInPrevMonth = prevMonth.lengthOfMonth()
            val totalCells = ((startOffset + daysInMonth + 6) / 7) * 7

            val cells = (0 until totalCells).map { i ->
                when {
                    i < startOffset ->
                        prevMonth.atDay(daysInPrevMonth - startOffset + i + 1) to false
                    i < startOffset + daysInMonth ->
                        currentYearMonth.atDay(i - startOffset + 1) to true
                    else ->
                        nextMonth.atDay(i - startOffset - daysInMonth + 1) to false
                }
            }

            cells.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { (date, isCurrentMonth) ->
                        CalendarDayCell(
                            date = date,
                            isCurrentMonth = isCurrentMonth,
                            startDate = normalizedStartDate,
                            endDate = normalizedEndDate,
                            onClick = { onDateSelected(date) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    date: LocalDate,
    isCurrentMonth: Boolean,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasRange = startDate != null && endDate != null
    val isStart = startDate != null && date == startDate
    val isEnd = endDate != null && date == endDate
    val isInRange = hasRange && date.isAfter(startDate) && date.isBefore(endDate)
    val isPendingStart = isStart && !hasRange

    val rangeBackground: Pair<androidx.compose.ui.graphics.Color, RoundedCornerShape>? = when {
        isStart && hasRange && !isEnd -> DduruTheme.colors.secondary to RoundedCornerShape(
            topStart = 99.dp,
            topEnd = 0.dp,
            bottomStart = 99.dp,
            bottomEnd = 0.dp
        )
        isEnd && !isStart -> DduruTheme.colors.secondary to RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 99.dp,
            bottomStart = 0.dp,
            bottomEnd = 99.dp
        )
        isInRange -> DduruTheme.colors.secondary to RoundedCornerShape(0.dp)
        else -> null
    }

    Box(
        modifier = modifier
            .height(44.dp)
            .then(
                rangeBackground?.let { (color, shape) ->
                    Modifier.background(color = color, shape = shape)
                } ?: Modifier
            )
            .then(if (!isCurrentMonth) Modifier.alpha(0.1f) else Modifier)
            .then(if (isCurrentMonth) Modifier.noRippleClickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        val circleModifier: Modifier = when {
            (isStart || isEnd) && hasRange ->
                Modifier
                    .size(44.dp)
                    .background(color = DduruTheme.colors.primary, shape = RoundedCornerShape(99.dp))
            isPendingStart ->
                Modifier
                    .size(44.dp)
                    .border(width = 1.dp, color = DduruTheme.colors.primary, shape = RoundedCornerShape(99.dp))
            else -> Modifier
        }

        val textColor = when {
            (isStart || isEnd) && hasRange -> DduruTheme.colors.gray0
            isPendingStart || isInRange -> DduruTheme.colors.primary
            else -> DduruTheme.colors.black
        }

        Box(
            modifier = circleModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = DduruTheme.typography.Body2,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DduruCalendarNoSelectionPreview() {
    DduruTheme {
        DduruCalendar(
            selectedStartDate = null,
            selectedEndDate = null,
            onDateSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DduruCalendarPendingStartPreview() {
    DduruTheme {
        DduruCalendar(
            selectedStartDate = LocalDate.of(2026, 7, 17),
            selectedEndDate = null,
            onDateSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DduruCalendarRangeSelectedPreview() {
    DduruTheme {
        DduruCalendar(
            selectedStartDate = LocalDate.of(2026, 7, 17),
            selectedEndDate = LocalDate.of(2026, 7, 23),
            onDateSelected = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
