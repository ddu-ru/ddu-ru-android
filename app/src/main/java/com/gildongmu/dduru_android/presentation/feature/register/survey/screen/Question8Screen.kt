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
fun Question8Screen(selectedOption: Int?, onOptionSelected: (Int) -> Unit, onBackClick: () -> Unit) {
    ThreeOptionQuestionScreen(
        questionNumber = 8,
        questionTitle = "여행 계획 좀 세워볼까?",
        imageRes = R.drawable.img_q6_1,
        options = listOf("📅 시간별 디테일한 일정표!", "📘 큰 틀만 잡고 상황 따라 융통성 있게 조정", "📍 가고 싶은 곳만 정하고 현장에서 결정!"),
        selectedOption = selectedOption, onOptionSelected = onOptionSelected, onBackClick = onBackClick
    )
}

@Composable
fun Question9Screen(selectedOption: Int?, onOptionSelected: (Int) -> Unit, onBackClick: () -> Unit) {
    ThreeOptionQuestionScreen(
        questionNumber = 9,
        questionTitle = "감자기 특이한 메뉴를 추천받았다!",
        imageRes = R.drawable.img_q9,
        options = listOf("☕ 좀... 원래 고르는 메뉴가 더 안전하지!", "🔍 후기부터 확인해야지, 구글링 출동!", "📖 이렇때 아니면 언제? 바로 가야지!!"),
        selectedOption = selectedOption, onOptionSelected = onOptionSelected, onBackClick = onBackClick
    )
}

@Composable
fun Question10Screen(selectedOption: Int?, onOptionSelected: (Int) -> Unit, onBackClick: () -> Unit) {
    ThreeOptionQuestionScreen(
        questionNumber = 10,
        questionTitle = "친구의 지인이 동행하자고 한다면?",
        imageRes = R.drawable.img_q10,
        options = listOf("😤 새 친구 생기면 좋지! 완전 환영!", "🙂 어떤 사람일까? 잘 맞으면 재밌을 듯!", "🤷 우리끼리 여행이 좋아 정중히 거절!"),
        selectedOption = selectedOption, onOptionSelected = onOptionSelected, onBackClick = onBackClick
    )
}

@Composable
fun Question11Screen(selectedOption: Int?, onOptionSelected: (Int) -> Unit, onBackClick: () -> Unit) {
    ThreeOptionQuestionScreen(
        questionNumber = 11,
        questionTitle = "기억하고 싶은 순간!",
        imageRes = R.drawable.img_q11,
        options = listOf("🎒 사전은 무조건 없이! 안전상 날짜기", "😁 좀... 상관 없음! 동행자 스타일에 맞출래", "👀 사진보단 눈으로 담는 게 진짜지"),
        selectedOption = selectedOption, onOptionSelected = onOptionSelected, onBackClick = onBackClick
    )
}

@Composable
private fun ThreeOptionQuestionScreen(
    questionNumber: Int,
    questionTitle: String,
    imageRes: Int,
    options: List<String>,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = DduruTheme.colors.Gray50),
        contentAlignment = Alignment.TopCenter
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart).padding(top = 40.dp, start = 16.dp)
        )

        PenguinCharacter(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 100.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionCard(
                questionNumber = questionNumber,
                questionTitle = questionTitle,
                imageRes = imageRes,
                modifier = Modifier.width(343.dp).height(320.dp)
            )

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                options.forEachIndexed { index, option ->
                    SurveyOptionButton(
                        text = option,
                        isSelected = selectedOption == index +1,
                        onClick = { onOptionSelected(index) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Question8ScreenPreview() {
    DduruTheme {
        Question8Screen(selectedOption = null, onOptionSelected = {}, onBackClick = {})
    }
}
