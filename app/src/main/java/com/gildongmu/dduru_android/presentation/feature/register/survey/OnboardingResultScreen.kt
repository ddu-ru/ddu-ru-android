package com.gildongmu.dduru_android.presentation.feature.register.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.survey.component.SurveyResultDetail

@Composable
fun OnboardingResultScreen(
    modifier: Modifier,
    onNextClick: () -> Unit
){
    Column (
        modifier = modifier
            .background(DduruTheme.colors.Gray50)
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "내 여행 성격",
            style = DduruTheme.typography.TitleMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.padding(30.dp))

        SurveyResultDetail()

        Spacer(modifier = Modifier.padding(12.dp))

        DduruButton(
            "네 맞아요!",
            { onNextClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 78.dp)
        )
    }
}
