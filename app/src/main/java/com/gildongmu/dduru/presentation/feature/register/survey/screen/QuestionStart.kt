package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.BubbleArrowDirection
import com.gildongmu.dduru.presentation.component.SpeechBubble
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur

@Composable
fun QuestionStart(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.gray50)
            .padding(top = 100.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpeechBubble(
            modifier = Modifier.width(325.dp),
            direction = BubbleArrowDirection.Bottom,
            cornerRadius = 24.dp,
            containerColor = DduruTheme.colors.gray0
        ) {
            Text(
                text = "뚜르에서 더 나은 매칭을 위해\n재밌는 설문이 필요해요. 금방 끝난답니다!",
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.width(267.dp).height(267.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shadow),
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier
                    .alpha(0.3f)
                    .blur(radius = 15.dp)
                    .padding(1.dp)
                    .width(70.dp)
                    .height(8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.img_survey_start),
                contentDescription = "뚜비 캐릭터",
                modifier = Modifier
                    .width(278.dp)
                    .height(278.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(83.dp))

        DduruButton(
            text = "네, 좋아요!",
            onClick = onNextClick,
            modifier = Modifier.width(343.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "다음에 할게요",
            modifier = Modifier.clickable { onGuestClick() },
            style = DduruTheme.typography.Body3,
            color = DduruTheme.colors.gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionStartPreview() {
    DduruTheme {
        QuestionStart(
            onNextClick = {},
            onGuestClick = {}
        )
    }
}
