package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.survey.component.SurveyResultDetail
import com.gildongmu.dduru.presentation.feature.register.survey.state.SurveyResultState
import com.gildongmu.dduru.presentation.feature.register.survey.viewmodel.OnboardingResultViewModel

@Composable
fun OnboardingResultRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingResultViewModel = viewModel(),
    onNextClick: () -> Unit
) {
    OnboardingResultScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onNextClick = onNextClick
    )
}
@Composable
fun OnboardingResultScreen(
    uiState: SurveyResultState,
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.gray50,
        bottomBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                DduruButton(
                    text = "네 맞아요!",
                    onClick = onNextClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 94.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                text = "내 여행 성격",
                style = DduruTheme.typography.H1,
                textAlign = TextAlign.Center
            )

            SurveyResultDetail(
                modifier = Modifier.weight(3f),
                uiModel = uiState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingResultScreenPreview() {
    DduruTheme {
        OnboardingResultScreen(
            modifier = Modifier.fillMaxWidth(),
            uiState = SurveyResultState(
                avatarName = "감성 펭귄",
                avatarDescription = "혼자만의 여유를 즐기며 \n여행하는 타입이에요.",
                imageUrl = R.drawable.img_penguin_personallity,
                personality = "차분하고 섬세해요.",
                strength = "계획 없이도 흐름을 잘 타요.",
                tip = "너무 혼자만 다니지 않기!",
                tags = listOf("감성", "힐링", "자유")
            ),
            onNextClick = {}
        )
    }
}
