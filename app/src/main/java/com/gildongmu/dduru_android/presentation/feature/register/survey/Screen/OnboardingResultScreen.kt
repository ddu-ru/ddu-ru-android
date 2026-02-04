package com.gildongmu.dduru_android.presentation.feature.register.survey.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.survey.component.SurveyResultDetail

@Composable
fun OnboardingResultScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
){
    Column (
        modifier = modifier
            .background(DduruTheme.colors.Gray50)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            modifier = Modifier
                .padding(top = 78.dp)
                .weight(1f)
            ,
            text = "내 여행 성격",
            style = DduruTheme.typography.TitleLarge,
            textAlign = TextAlign.Center,
        )

        SurveyResultDetail(
            modifier = Modifier.weight(3f)
        )

        DduruButton(
            "네 맞아요!",
            { onNextClick()},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 78.dp, top = 31.dp)
        )
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