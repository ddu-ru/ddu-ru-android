package com.gildongmu.dduru.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeNotificationRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeNotificationScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun HomeNotificationScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
    ) {
        HomeSubPageTopBar(
            title = "알림",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "알림",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeNotificationScreenPreview() {
    DduruTheme {
        HomeNotificationScreen(onBackClick = {})
    }
}
