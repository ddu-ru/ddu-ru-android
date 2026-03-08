package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun TripDateRange(
    date: String, textColor: Color,
    modifier: Modifier = Modifier, textStyle: TextStyle = DduruTheme.typography.Caption2
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_home_calendar), contentDescription = null, tint = textColor
        )

        Text(
            text = date, color = textColor, style = textStyle
        )
    }
}

@Preview
@Composable
private fun TripDateRangeWithYearPreview() {
    DduruTheme {
        TripDateRange(
            date = "2026.03.15-03.18", textColor = DduruTheme.colors.gray600
        )
    }
}

@Preview
@Composable
private fun TripDateRangeWithoutYearPreview() {
    DduruTheme {
        TripDateRange(
            date = "03.15-03.18", textColor = DduruTheme.colors.gray500
        )
    }
}
