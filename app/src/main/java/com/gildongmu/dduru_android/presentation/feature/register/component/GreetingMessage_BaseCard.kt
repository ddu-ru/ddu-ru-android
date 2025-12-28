package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupStep

class CustomCardShape(
    private val radius: Dp = 28.dp,
    private val tailWidth: Dp = 18.dp,
    private val tailHeight: Dp = 28.dp,
    private val tailOffset: Dp = 32.dp
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
    modifier: Modifier = Modifier,
    current: Int,
    total: Int
) {
    val badgeBackground = Color(0xFFBBD8FF)
    Box(
        modifier = modifier
            .background(
                color = badgeBackground,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = "STEP  $current / $total",
            style = DduruTheme.typography.bodySmall,
            color = DduruTheme.colors.TextOnButtonActive
        )
    }
}

@Composable
fun GreetingMessageCard(
    modifier: Modifier = Modifier,
    step: SignupStep
) {
    val shape = CustomCardShape()
    val cardWidth = 280.dp
    val penguinSize = 120.dp
    val betweenWidth = 24.dp
    val totalWidth = cardWidth + penguinSize + betweenWidth
    val totalHeight = totalWidth / 3
    val stepContents = listOf(
        SignupStep.BIRTH to ("생년월일을 알려주세요" to "정확한 나이를 확인해요"),
        SignupStep.GENDER to ("성별을 선택해주세요" to "추천에 사용돼요"),
        SignupStep.NICKNAME to ("닉네임을 입력해주세요" to "회원님을 뭐라고 부를까요?"),
        SignupStep.PHONE to ("전화번호를 입력해주세요" to "본인 확인을 위해 필요해요"),
        SignupStep.AUTH to ("인증번호를 입력해주세요" to "문자로 받은 번호를 입력하세요"),
        SignupStep.DONE to ("가입이 완료됐어요" to "이제 두루두루 즐겨봐요!")
    )
    val totalSteps = stepContents.size
    val currentIndex = stepContents.indexOfFirst { it.first == step }.let {
        if (it == -1) totalSteps - 1 else it
    }
    val (title, desc) = stepContents.getOrElse(currentIndex) { stepContents.last() }.second

    Box(
        modifier = modifier.padding(horizontal = 0.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .width(totalWidth)
                .height(totalHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(cardWidth)
                    .fillMaxHeight()
                    .shadow(8.dp, shape, clip = false)
                    .background(Color.White, shape)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        StepBadge(
                            modifier = Modifier.padding(bottom = 12.dp),
                            current = currentIndex + 1,
                            total = totalSteps
                        )

                        Text(
                            text = title,
                            style = DduruTheme.typography.titleMedium,
                            color = DduruTheme.colors.TextMain,
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                        Text(
                            text = desc,
                            style = DduruTheme.typography.bodyMedium,
                            color = DduruTheme.colors.TextSub
                        )
                    }


                }
            }

            Image(
                painter = painterResource(id = R.drawable.haha),
                contentDescription = null,
                modifier = Modifier.size(penguinSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingMessageCardPreview() {
    DduruTheme {
        GreetingMessageCard(step = SignupStep.BIRTH)
    }
}
