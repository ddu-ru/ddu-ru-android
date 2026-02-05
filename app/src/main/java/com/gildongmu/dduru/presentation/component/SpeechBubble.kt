package com.gildongmu.dduru.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class BubbleArrowDirection {
    Bottom, Right
}

private class UniversalSpeechBubbleShape(
    private val direction: BubbleArrowDirection = BubbleArrowDirection.Bottom,
    private val cornerRadius: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
    private val arrowOffset: Dp = 0.dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = with(density) {

        val cr = cornerRadius.toPx()
        val aw = arrowWidth.toPx()
        val ah = arrowHeight.toPx()
        val offset = arrowOffset.toPx()

        val path = Path()

        when (direction) {
            BubbleArrowDirection.Bottom -> {
                val bodyHeight = (size.height - ah).coerceAtLeast(0f)
                val centerX = size.width / 2f + offset

                path.apply {
                    addRoundRect(RoundRect(Rect(0f, 0f, size.width, bodyHeight), CornerRadius(cr, cr)))
                    moveTo(centerX - aw / 2f, bodyHeight)
                    lineTo(centerX, bodyHeight + ah)
                    lineTo(centerX + aw / 2f, bodyHeight)
                    close()
                }
            }
            BubbleArrowDirection.Right -> {
                val bodyWidth = (size.width - aw).coerceAtLeast(0f)
                val centerY = size.height / 2f + offset

                path.apply {
                    addRoundRect(RoundRect(Rect(0f, 0f, bodyWidth, size.height), CornerRadius(cr, cr)))
                    moveTo(bodyWidth, centerY - ah / 2f)
                    lineTo(bodyWidth + aw, centerY)
                    lineTo(bodyWidth, centerY + ah / 2f)
                    close()
                }
            }
        }

        Outline.Generic(path)
    }
}

@Composable
fun SpeechBubble(
    modifier: Modifier = Modifier,
    direction: BubbleArrowDirection = BubbleArrowDirection.Bottom, // 방향 인자 추가
    cornerRadius: Dp = 24.dp,
    arrowWidth: Dp = 22.dp,
    arrowHeight: Dp = 12.dp,
    arrowOffset: Dp = 0.dp,
    shadowElevation: Dp = 12.dp,
    containerColor: Color = Color.White,
    content: @Composable () -> Unit
) {

    val shape = UniversalSpeechBubbleShape(
        direction = direction,
        cornerRadius = cornerRadius,
        arrowWidth = arrowWidth,
        arrowHeight = arrowHeight,
        arrowOffset = arrowOffset
    )

    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation,
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
