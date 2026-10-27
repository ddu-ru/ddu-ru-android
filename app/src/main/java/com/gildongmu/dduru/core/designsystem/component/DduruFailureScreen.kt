package com.gildongmu.dduru.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun DduruFailureScreen(
    message: String,
    onRetryClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray600
            )
            if (onRetryClick != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetryClick) {
                    Text(text = "다시 시도")
                }
            }
        }
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
