package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
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

@Preview(showBackground = true)
@Composable
private fun DduruLoadingScreenPreview() {
    DduruTheme {
        DduruLoadingScreen()
    }
}
