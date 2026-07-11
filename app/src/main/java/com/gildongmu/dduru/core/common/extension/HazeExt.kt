package com.gildongmu.dduru.core.common.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun rememberDduruHazeState(): HazeState = remember { HazeState() }

fun Modifier.dduruHazeBackground(hazeState: HazeState): Modifier = haze(state = hazeState)

@Composable
fun Modifier.dduruFrostedSurface(
    hazeState: HazeState,
    shape: Shape = RoundedCornerShape(24.dp)
): Modifier = this
    .hazeChild(
        state = hazeState,
        shape = shape,
        style = HazeStyle(
            tint = DduruTheme.colors.gray50.copy(alpha = 0.18f),
            blurRadius = 8.dp
        )
    )

@Composable
fun Modifier.dduruFrostedBackground(): Modifier = this
    .background(
        brush = Brush.verticalGradient(
            colorStops = arrayOf(
                0f to DduruTheme.colors.gray500.copy(alpha = 0.1f),
                0.2f to Color.Transparent,
                0.8f to Color.Transparent,
                1f to DduruTheme.colors.gray50.copy(alpha = 0.9f)
            )
        )
    )

@Composable
fun Modifier.dduruFrostedBorder(
    shape: Shape = RoundedCornerShape(24.dp)
): Modifier = this
    .border(
        width = 1.dp,
        color = DduruTheme.colors.gray0.copy(alpha = 0.5f),
        shape = shape
    )
