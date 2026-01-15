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

// 개별 질문 화면들 (Q2 ~ Q6)
@Composable
fun Question2Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    StandardQuestionScreen(
        questionNumber = 2,
        questionTitle = "워! 유명한 맛집인데.. \n대기가 2시간😵",
        imageRes = R.drawable.img_q2,
        options = listOf("😎 이왕이면 기다려서라도 먹어야지!", "🤬 기다리기는 좀... 편하게 주변에서 먹자"),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onBackClick = onBackClick
    )
}

@Composable
fun Question3Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    StandardQuestionScreen(
        questionNumber = 3,
        questionTitle = "여행 준비 완료!\n그런데 숙소는...",
        imageRes = R.drawable.img_q3,
        options = listOf("📚 잠은 괜찮은 곳에서 자야지", "😴 잠만 잘 수 있으면 OK!"),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onBackClick = onBackClick
    )
}

@Composable
fun Question4Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    StandardQuestionScreen(
        questionNumber = 4,
        questionTitle = "돈 관리는 중요해대..\n우리 경비는 여떻게 할까?",
        imageRes = R.drawable.img_q4,
        options = listOf("💵 돈 관리는 중요해! 꼼꼼하게", "🎨 큰 틀만 잡고 상황 따라 융통성 있게 조정"),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onBackClick = onBackClick
    )
}

@Composable
fun Question5Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    StandardQuestionScreen(
        questionNumber = 5,
        questionTitle = "돈 관리는 중요해대..\n우리 경비는 여떻게 할까?",
        imageRes = R.drawable.img_q5,
        options = listOf("🚌 한 통장에 모아 함께 쓰기!", "😁 기다리기는 좀... 펀하게 주변에서 먹자"),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onBackClick = onBackClick
    )
}

@Composable
fun Question6Screen(
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    StandardQuestionScreen(
        questionNumber = 6,
        questionTitle = "여행지에서 지갑을 여는 순간! 🤑\n나는...",
        imageRes = R.drawable.img_q6,
        options = listOf("😎 여행갔으면 써야지! 나는...", "🤑 아끼래도 가성비 최고지~"),
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        onBackClick = onBackClick
    )
}

// 공통 레이아웃
@Composable
private fun StandardQuestionScreen(
    questionNumber: Int,
    questionTitle: String,
    imageRes: Int,
    options: List<String>,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Gray50),
        contentAlignment = Alignment.TopCenter
    ) {
        // 뒤로가기
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
        )

        // 펭귄 (중앙 정렬)
        PenguinCharacter(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        )

        // 메인 컨텐츠
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 질문 카드
            QuestionCard(
                questionNumber = questionNumber,
                questionTitle = questionTitle,
                imageRes = imageRes,
                modifier = Modifier
                    .width(343.dp)
                    .height(320.dp)
            )

            Spacer(modifier = Modifier.height(52.dp))

            // 선택지 리스트
            Column(
                modifier = Modifier.width(343.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                options.forEachIndexed { index, option ->
                    SurveyOptionButton(
                        text = option,
                        isSelected = selectedOption == index,
                        onClick = { onOptionSelected(index) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Question2ScreenPreview() {
    DduruTheme {
        Question2Screen(selectedOption = null, onOptionSelected = {}, onBackClick = {})
    }
}
