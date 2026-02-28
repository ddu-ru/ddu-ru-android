package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.BubbleArrowDirection
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep

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
            text = "STEP $current / $total",
            style = DduruTheme.typography.BodySmall,
            color = Color.White
        )
    }
}

@Composable
fun GreetingMessageCard(
    modifier: Modifier = Modifier,
    step: SignupStep
) {
    val stepContents = listOf(
        SignupStep.BIRTH to ("생년월일을 알려주세요" to "정확한 나이를 확인해요"),
        SignupStep.GENDER to ("성별을 선택해주세요" to "추천에 사용돼요"),
        SignupStep.PHONE to ("전화번호를 입력해주세요" to "본인 확인을 위해 필요해요"),
        SignupStep.AUTH to ("인증번호를 입력해주세요" to "문자로 받은 번호를 입력하세요"),
        SignupStep.DONE to ("가입이 완료됐어요" to "이제 두루두루 즐겨봐요!")
    )
    val totalSteps = stepContents.size
    val currentIndex = stepContents.indexOfFirst { it.first == step }.let {
        if (it == -1) totalSteps - 1 else it
    }
    val (title, desc) = stepContents.getOrElse(currentIndex) { stepContents.last() }.second

    val cardWidth = 257.dp
    val penguinSize = 115.dp

    Row(
        modifier = modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SpeechBubble(
            modifier = Modifier.width(cardWidth),
            direction = BubbleArrowDirection.Right,
            arrowHeight = 22.dp,
            arrowWidth = 12.dp,
            containerColor = Color.White
        ) {
            // 말풍선 내부 콘텐츠
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                StepBadge(
                    modifier = Modifier.padding(bottom = 12.dp),
                    current = currentIndex + 1,
                    total = totalSteps
                )

                Text(
                    text = title,
                    style = DduruTheme.typography.TitleMedium,
                    color = DduruTheme.colors.Gray900,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = desc,
                    style = DduruTheme.typography.BodyMedium,
                    color = DduruTheme.colors.Gray600
                )
            }
        }

        // 4. 펭귄 이미지
        Image(
            painter = painterResource(id = R.drawable.haha),
            contentDescription = null,
            modifier = Modifier
                .size(penguinSize)
        )
    }
}
