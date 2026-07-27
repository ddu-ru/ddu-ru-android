package com.gildongmu.dduru.core.common.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@Composable
fun Modifier.dduruDualShadow(
    firstAlpha: Float,
    secondAlpha: Float,
    shape: Shape = RectangleShape,
    firstElevation: Dp = 16.dp,
    secondElevation: Dp = 4.dp,
    clip: Boolean = false
): Modifier = this
    .shadow(
        elevation = firstElevation,
        shape = shape,
        spotColor = DduruTheme.colors.black.copy(alpha = firstAlpha),
        ambientColor = DduruTheme.colors.black.copy(alpha = firstAlpha),
        clip = clip
    )
    .shadow(
        elevation = secondElevation,
        shape = shape,
        spotColor = DduruTheme.colors.black.copy(alpha = secondAlpha),
        ambientColor = DduruTheme.colors.black.copy(alpha = secondAlpha),
        clip = clip
    )
