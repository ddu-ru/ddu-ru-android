package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.survey.component.SurveyResultDetail
import com.gildongmu.dduru.presentation.feature.register.survey.state.SurveyResultState
import com.gildongmu.dduru.presentation.feature.register.survey.viewmodel.OnboardingResultViewModel

@Composable
fun OnboardingResultRoute(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: OnboardingResultViewModel = hiltViewModel(),
    onNextClick: () -> Unit
) {
    OnboardingResultScreen(
        modifier = modifier,
        paddingValues = paddingValues,
        uiState = viewModel.uiState,
        onNextClick = onNextClick
    )
}

@Composable
private fun OnboardingResultScreen(
    uiState: SurveyResultState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp),
            text = "내 여행 성격",
            style = DduruTheme.typography.H1,
            textAlign = TextAlign.Center
        )

        SurveyResultDetail(
            avatarName = uiState.avatarName,
            avatarDescription = uiState.avatarDescription,
            imageUrl = uiState.imageUrl,
            personality = uiState.personality,
            strength = uiState.strength,
            tip = uiState.tip,
            tags = uiState.tags
        )

        DduruButton(
            text = "네 맞아요!",
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 94.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingResultScreenPreview() {
    DduruTheme {
        OnboardingResultScreen(
            modifier = Modifier.fillMaxWidth(),
            uiState = SurveyResultState(
                avatarName = "감성 펭귄",
                avatarDescription = "혼자만의 여유를 즐기며 \n여행하는 타입이에요.",
                imageUrl = "https://i.postimg.cc/MTD5tQ1Y/img-penguin-personallity.png",
                personality = "차분하고 섬세해요.",
                strength = "계획 없이도 흐름을 잘 타요.",
                tip = "너무 혼자만 다니지 않기!",
                tags = listOf("감성", "힐링", "자유")
            ),
            paddingValues = PaddingValues(),
            onNextClick = {}
        )
    }
}
