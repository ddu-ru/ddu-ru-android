package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PollDatePicker(
    dateText: String,
    timeText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = DduruTheme.colors.gray200

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "투표 종료 날짜",
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray600
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val stroke = 2.dp.toPx()
                    val half = stroke / 2f
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height - half),
                        end = Offset(size.width, size.height - half),
                        strokeWidth = stroke
                    )
                }
                .height(48.dp)
                .padding(start = 16.dp, top = 3.dp, end = 16.dp, bottom = 3.dp)
                .noRippleClickable(onClick = onClick)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_home_calendar),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = DduruTheme.colors.gray800
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dateText,
                    style = DduruTheme.typography.Body1,
                    color = DduruTheme.colors.gray800
                )
                Text(
                    text = timeText,
                    style = DduruTheme.typography.Body1,
                    color = DduruTheme.colors.gray800
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = DduruTheme.colors.gray600
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PollDatePickerPreview() {
    DduruTheme {
        PollDatePicker(
            dateText = "2026. 4. 14 (화)",
            timeText = "03:15",
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
