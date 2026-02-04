package com.gildongmu.dduru_android.presentation.feature.register.survey.component

import androidx.compose.runtime.getValue
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.Composable

@Composable
fun LoadingFlashingAnimation(
    dotCount: Int,
    minAlpha: Float = 0.1f,
    delayUnit: Int = 300
): List<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    val duration = delayUnit * (dotCount + 1)

    return List(dotCount) { index ->
        val delay = index * delayUnit
        val alpha by infiniteTransition.animateFloat(
            initialValue = minAlpha,
            targetValue = minAlpha,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = duration
                    minAlpha at delay
                    1f at delay + delayUnit
                    minAlpha at delay + delayUnit * 2
                }
            )
        )
        alpha
    }
}
