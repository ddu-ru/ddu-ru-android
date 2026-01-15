package com.gildongmu.dduru_android.presentation.feature.register.survey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.R
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.survey.components.*

@Composable
fun Question1Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Gray50),
        contentAlignment = Alignment.TopCenter
    ) {
        // 1. 뒤로가기
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
        )

        // 2. 펭귄 (중앙 정렬)
        PenguinCharacter(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        )

        // 3. 컨텐츠
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionCard(
                questionNumber = 1,
                questionTitle = "다음 여행지로 출발~ \n어떻게 가지?",
                imageRes = R.drawable.img_q1,
                modifier = Modifier.width(343.dp).height(320.dp)
            )

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                SurveyOptionButton(
                    text = "🚶여행은 낭만이지! 걷거나 버스",
                    isSelected = selectedOption == 0,
                    onClick = { onOptionSelected(0) }
                )
                SurveyOptionButton(
                    text = "🚕 편한게 최고~ 택시 타는 거 어때?",
                    isSelected = selectedOption == 1,
                    onClick = { onOptionSelected(1) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Question1ScreenPreview() {
    DduruTheme {
        Question1Screen(selectedOption = null, onOptionSelected = {}, onBackClick = {})
    }
}
