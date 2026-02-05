package com.gildongmu.dduru.presentation.feature.register.survey.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SpeechBubble
import com.gildongmu.dduru.presentation.feature.register.survey.state.SurveyResultUiState


@Composable

fun SurveyResultDetail(
    uiModel: SurveyResultUiState,
    modifier: Modifier = Modifier,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        SpeechBubble(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                text = uiModel.avatarDescription,
                style = DduruTheme.typography.BodyLarge,
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
            style = DduruTheme.typography.TitleMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .padding(8.dp, bottom = 24.dp),
            text = uiModel.tags.joinToString(" ") { "#$it" },
            style = DduruTheme.typography.LabelMedium,
            textAlign = TextAlign.Center,
            color = DduruTheme.colors.Gray600
        )
        ResultDescriptionBox(
            items = listOf(
                uiModel.personality,
                uiModel.strength,
                uiModel.tip,
            )
        )
    }
}
