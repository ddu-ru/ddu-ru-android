package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyRoute(
    modifier: Modifier = Modifier
) {
    JourneyScreen(modifier = modifier)
}

@Composable
private fun JourneyScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "나의 여정",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.gray900
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyScreenPreview() {
    DduruTheme {
        JourneyScreen()
    }
}
