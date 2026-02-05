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
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.survey.component.SurveyResultDetail
import com.gildongmu.dduru.presentation.feature.register.survey.viewmodel.OnboardingResultViewModel

@Composable
fun OnboardingResultScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingResultViewModel = viewModel(),
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DduruTheme.colors.Gray50,
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
                    modifier = Modifier.fillMaxWidth().padding(bottom = 94.dp)
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
                style = DduruTheme.typography.TitleLarge,
                textAlign = TextAlign.Center,
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
            onNextClick = {}
        )
    }
}
