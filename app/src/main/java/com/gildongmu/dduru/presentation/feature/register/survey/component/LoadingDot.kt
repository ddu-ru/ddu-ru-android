package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LoadingDot(
    alpha: Float,
    size: Dp = 6.dp
) {
    Spacer(
        modifier = Modifier
            .size(size)
            .alpha(alpha)
            .background(
                color = DduruTheme.colors.Primary,
                shape = CircleShape
            )
    )
}
