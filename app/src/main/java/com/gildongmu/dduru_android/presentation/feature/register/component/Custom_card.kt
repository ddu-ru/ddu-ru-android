package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

class CustomCardShape(
    private val radius: Dp = 16.dp,
    private val tailWidth: Dp = 12.dp,
    private val tailHeight: Dp = 16.dp,
    private val tailOffset: Dp = 12.dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = with(density) {

        val r = radius.toPx()
        val tw = tailWidth.toPx()
        val th = tailHeight.toPx()
        val offsetY = tailOffset.toPx()
        val centerY = size.height / 2f + offsetY

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = size.width - tw,
                        bottom = size.height
                    ),
                    cornerRadius = CornerRadius(r, r)
                )
            )

            moveTo(size.width - tw, centerY - th / 2f)
            lineTo(size.width, centerY)
            lineTo(size.width - tw, centerY + th / 2f)
            close()
        }

        Outline.Generic(path)
    }
}


@Composable
fun StepBadge(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = DduruTheme.colors.StepBadgeBg,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "STEP   0 / 0",
            style = DduruTheme.typography.bodySmall,
            color = DduruTheme.colors.MainColor
        )
    }
}

@Composable
fun GreetingMessageCard(
    modifier: Modifier = Modifier
) {
    val shape = CustomCardShape()

    Box(
        modifier = modifier
            .padding(vertical = 60.dp, horizontal = 16.dp)
            .shadow(8.dp, shape, clip = false)
            .background(Color.White, shape)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Column {

            StepBadge(
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "content",
                style = DduruTheme.typography.titleMedium,
                color = DduruTheme.colors.MainColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Detail_Content",
                style = DduruTheme.typography.bodyMedium,
                color = DduruTheme.colors.MainColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingMessageCardPreview() {
    DduruTheme {
        GreetingMessageCard()
    }
}
