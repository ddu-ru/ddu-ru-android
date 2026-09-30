package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleTimeSection(
    startTime: String?,
    endTime: String?,
    isEndTimeAdded: Boolean,
    errorMessage: String?,
    onStartTimeClick: () -> Unit,
    onAddEndTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "시간",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(8.dp))

        JourneyScheduleTimeRow(
            label = "시작 시간",
            time = startTime,
            onClick = onStartTimeClick
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (isEndTimeAdded) {
            JourneyScheduleTimeRow(
                label = "종료 시간",
                time = endTime,
                onClick = onEndTimeClick
            )
        } else {
            Row(
                modifier = Modifier.noRippleClickable(
                    onClick = onAddEndTimeClick
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_gray),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = DduruTheme.colors.gray400
                )
                Text(
                    text = "종료시간 추가",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400
                )
            }
        }

        JourneyScheduleTimeValidationMessage(message = errorMessage)
    }
}

@Composable
private fun JourneyScheduleTimeRow(
    label: String,
    time: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )

        Text(
            text = time ?: "미정",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(DduruTheme.colors.gray100)
                .noRippleClickable(onClick = onClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                ),
            style = DduruTheme.typography.Body2,
            color = if (time == null) {
                DduruTheme.colors.gray600
            } else {
                DduruTheme.colors.gray800
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleTimeSectionPreview() = DduruTheme {
    JourneyScheduleTimeSection(
        startTime = "오전 10:00",
        endTime = "",
        isEndTimeAdded = true,
        errorMessage = "종료 시간은 시작 시간 이후로 설정해 주세요.",
        onStartTimeClick = {},
        onAddEndTimeClick = {},
        onEndTimeClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
