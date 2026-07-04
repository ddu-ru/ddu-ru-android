package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ParticipationStatusBadge(
    label: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(99.dp))
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Micro2,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipationStatusBadgePreview() {
    DduruTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ParticipationStatusBadge(
                label = "연락중",
                backgroundColor = DduruTheme.colors.secondary,
                textColor = DduruTheme.colors.primary
            )
            ParticipationStatusBadge(
                label = "연락 대기중",
                backgroundColor = DduruTheme.colors.gray200,
                textColor = DduruTheme.colors.gray600
            )
            ParticipationStatusBadge(
                label = "수락됨",
                backgroundColor = DduruTheme.colors.acceptGreenBg,
                textColor = DduruTheme.colors.acceptGreen
            )
            ParticipationStatusBadge(
                label = "거절됨",
                backgroundColor = DduruTheme.colors.rejectRedBg,
                textColor = DduruTheme.colors.rejectRed
            )
        }
    }
}
