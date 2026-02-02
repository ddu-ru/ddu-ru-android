package com.gildongmu.dduru_android.presentation.feature.register.survey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.survey.components.*

@Composable
fun Question7Screen(
    selectedOptions: Set<Int>,
    onOptionToggled: (Int) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    isNextButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        "🏛️ 관광", "⚽ 관람", "🥦 자연 탐방", "🍅 먹방",
        "🎁 쇼핑", "🎨 휴양", "🏃 액티비티",
        "🎡 놀이공원",
        "🔥 페스티벌"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = DduruTheme.colors.Gray50),
        contentAlignment = Alignment.TopCenter
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
        )

        PenguinCharacter(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 55.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionCard(
                questionNumber = 7,
                questionTitle = "여행 가서 주로 뭐 하고 싶어?  (최대 3개)",
                imageRes = null,
                modifier = Modifier
                    .width(343.dp)
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp),
                modifier = Modifier.width(343.dp)
            ) {
                itemsIndexed(options) { index, option ->
                    SurveyGridOptionButton(
                        text = option,
                        isSelected = selectedOptions.contains(index + 1),
                        onClick = { onOptionToggled(index) }, // 클릭은 그대로 index(0,1...)를 보내면 뷰모델이 +1 해서 처리함
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (selectedOptions.isNotEmpty()) {
            Button(
                onClick = onNextClick,
                enabled = isNextButtonEnabled,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DduruTheme.colors.Primary,
                    contentColor = DduruTheme.colors.Gray0,
                    disabledContainerColor = DduruTheme.colors.Gray200,
                    disabledContentColor = DduruTheme.colors.Gray500
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "다음 (${selectedOptions.size}/3)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Question7ScreenPreview() {
    DduruTheme {
        Question7Screen(
            selectedOptions = setOf(1, 2),
            onOptionToggled = {},
            onNextClick = {},
            onBackClick = {},
            isNextButtonEnabled = true
        )
    }
}
