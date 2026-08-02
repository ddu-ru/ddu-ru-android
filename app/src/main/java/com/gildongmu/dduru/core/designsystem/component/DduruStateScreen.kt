package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = DduruTheme.colors.primary
        )
    }
}

@Composable
fun DduruFailureScreen(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DduruLoadingScreenPreview() {
    DduruTheme {
        DduruLoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun DduruFailureScreenPreview() {
    DduruTheme {
        DduruFailureScreen(
            message = "정보를 불러오지 못했어요."
        )
    }
}
