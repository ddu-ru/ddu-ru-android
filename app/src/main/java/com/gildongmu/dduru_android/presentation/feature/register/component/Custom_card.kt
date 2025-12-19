package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme

class CustomCardShape(
    private val radius: Dp = 16.dp,
    private val tailWidth: Dp = 10.dp,
    private val tailTopHeight: Dp = (-2).dp,    //tt(윗변)
    private val tailBottomHeight: Dp = 16.dp,  //tb (아랫변)
    private val tailOffset: Dp = 0.dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = with(density) {
        val r = radius.toPx()
        val tw = tailWidth.toPx()
        val tt = tailTopHeight.toPx()
        val tb = tailBottomHeight.toPx()
        val offsetY = tailOffset.toPx()
        val centerY = (size.height / 2f) + offsetY

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, size.width - tw, size.height),
                    cornerRadius = CornerRadius(r, r)
                )
            )

            moveTo(size.width - tw, centerY - tt)
            lineTo(size.width, centerY)
            lineTo(size.width - tw, centerY + tb)
            close()
        }
        Outline.Generic(path)
    }
}

@Composable
fun GreetingMessageCard(
    modifier: Modifier = Modifier
) {
    val shape = CustomCardShape(tailOffset = 8.dp)

    Box(
        modifier = modifier
//            .width(257.dp)
//            Todo() .height(98.dp)  나중에 메세지 카드 값 다 넣고 구현하기
            .padding(horizontal = 16.dp)
            .shadow(elevation = 8.dp, shape = shape, clip = false)
            .background(color = Color.White, shape = shape)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Step 1/4",
                color = DduruTheme.colors.MainColor,
                style = DduruTheme.typography.titleMedium
            )
            Text(
                text = "먼저 , 나이를 알려주세요 ",
                color = DduruTheme.colors.MainColor,
                style = DduruTheme.typography.titleMedium
            )
            Text(
                text = "정확한 나이를 알면 더 신뢰있는 만남이 가능해요 ",
                color = DduruTheme.colors.MainColor,
                style = DduruTheme.typography.titleMedium
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
