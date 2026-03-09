package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.home.component.HomeHashtag
import com.gildongmu.dduru.presentation.feature.register.survey.state.SurveyResultState

@Composable
fun SurveyResultDetail(
    uiModel: SurveyResultState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpeechBubble(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                text = uiModel.avatarDescription,
                style = DduruTheme.typography.Body1,
                textAlign = TextAlign.Center
            )
        }
        Image(
            painter = painterResource(uiModel.imageUrl),
            contentDescription = "뚜비 캐릭터",
            modifier = Modifier
                .width(202.dp)
                .height(213.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = uiModel.avatarName,
            style = DduruTheme.typography.H1,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.padding(bottom = 19.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiModel.tags.forEach { tag ->
                HomeHashtag(
                    text = tag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Caption2,
                    backgroundColor = DduruTheme.colors.gray300,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        ResultDescriptionBox(
            items = listOf(
                uiModel.personality,
                uiModel.strength,
                uiModel.tip
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun SurveyResultDetailPreview() {
    DduruTheme {
        SurveyResultDetail(
            uiModel = SurveyResultState(
                avatarName = "감성 펭귄",
                avatarDescription = "당신은 조용한 여행지에서 여유를 즐기는 타입이에요.",
                imageUrl = R.drawable.img_penguin_personallity,
                personality = "혼자만의 시간을 즐기며 차분하게 여행하는 편이에요.",
                strength = "낯선 환경에서도 금방 적응하고 여유를 찾을 수 있어요.",
                tip = "가끔은 즉흥적인 일정도 받아들이면 더 즐거운 여행이 될 수 있어요.",
                tags = listOf("감성", "힐링", "여유")
            )
        )
    }
}
