package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TravelDatesSection(
    dateRanges: List<String>,
    modifier: Modifier = Modifier,
    label: String = "선호 여행 날짜"
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            color = DduruTheme.colors.gray400,
            style = DduruTheme.typography.Caption2
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            dateRanges.forEach { dateRange ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(DduruTheme.colors.gray50)
                        .padding(8.dp)
                ) {
                    Text(
                        text = dateRange,
                        color = DduruTheme.colors.gray868,
                        style = DduruTheme.typography.Caption2
                    )
                }
            }
        }
    }
}

@Preview(name = "TravelDatesSection")
@Composable
private fun TravelDatesSectionPreview() {
    DduruTheme {
        TravelDatesSection(
            dateRanges = listOf(
                "2026.03.15 ~ 2026.03.20",
                "2026.05.02 ~ 2026.05.05"
            )
        )
    }
}
