package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.gildongmu.dduru.presentation.feature.register.survey.component.LoadingFlashing

@Composable
fun OnboardingAnalysisScreen(
    username: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SpeechBubble(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingFlashing()

                Spacer(Modifier.height(12.dp))

                Text(
                    modifier = Modifier.padding(),
                    text = "답변하신 내용을 바탕으로 ${username}의 여행 성격을 분석하고 있어요!",
                    style = DduruTheme.typography.Body1,
                    textAlign = TextAlign.Center
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_penguin_ver3),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 29.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingAnalysisScreenPreview() {
    DduruTheme {
        OnboardingAnalysisScreen(
            username = "겨울이오길"
        )
    }
}
