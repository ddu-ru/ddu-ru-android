package com.gildongmu.dduru_android.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

private class SpeechBubbleShape(
    private val cornerRadius: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
    private val arrowCenterOffsetX: Dp = 0.dp,
) : Shape {

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = with(density) {

        val cr = cornerRadius.toPx()
        val aw = arrowWidth.toPx()
        val ah = arrowHeight.toPx()
        val offsetX = arrowCenterOffsetX.toPx()

        // 꼬리 영역을 제외한 본체 높이
        val bodyHeight = (size.height - ah).coerceAtLeast(0f)

        val centerX = size.width / 2f + offsetX
        val halfArrowW = aw / 2f
        val baseLeftX = (centerX - halfArrowW).coerceIn(0f, size.width)
        val baseRightX = (centerX + halfArrowW).coerceIn(0f, size.width)

        val path = Path().apply {
            //본체 사각형
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, size.width, bodyHeight),
                    cornerRadius = CornerRadius(cr, cr)
                )
            )

            //꼬리 삼각형
            moveTo(baseLeftX, bodyHeight)
            lineTo(centerX, bodyHeight + ah)
            lineTo(baseRightX, bodyHeight)
            close()
        }

        Outline.Generic(path)
    }
}

@Composable
fun SpeechBubble(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    arrowWidth: Dp = 22.dp,
    arrowHeight: Dp = 12.dp,
    arrowCenterOffsetX: Dp = 0.dp,
    shadowElevation: Dp = 12.dp,
    containerColor: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(
        start = 24.dp,
        top = 24.dp,
        end = 24.dp,
        bottom = 24.dp + arrowHeight
    ),
    content: @Composable () -> Unit
) {
    val shape = SpeechBubbleShape(
        cornerRadius = cornerRadius,
        arrowWidth = arrowWidth,
        arrowHeight = arrowHeight,
        arrowCenterOffsetX = arrowCenterOffsetX
    )

    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        shadowElevation = shadowElevation,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
