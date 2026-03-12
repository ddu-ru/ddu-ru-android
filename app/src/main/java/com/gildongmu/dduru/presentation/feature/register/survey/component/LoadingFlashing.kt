package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LoadingFlashing(
    modifier: Modifier = Modifier
) {
    val alphas = LoadingFlashingAnimation(dotCount = 3)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val space = 8.dp

        alphas.forEachIndexed { index, alpha ->
            LoadingDot(alpha)
            if (index != alphas.lastIndex) {
                Spacer(Modifier.width(space))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingFlashPreview() {
    DduruTheme {
        LoadingFlashing()
    }
}
