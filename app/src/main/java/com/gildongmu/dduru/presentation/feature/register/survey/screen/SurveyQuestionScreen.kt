package com.gildongmu.dduru.presentation.feature.register.survey.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.Question
import com.gildongmu.dduru.domain.entity.QuestionOption
import com.gildongmu.dduru.domain.entity.QuestionType
import com.gildongmu.dduru.presentation.feature.register.survey.component.SurveyOptionButton
import com.gildongmu.dduru.presentation.feature.register.survey.component.SurveyQuestionLayout

@Composable
fun SurveyQuestionScreen(
    question: Question,
    totalQuestions: Int,
    selectedOption: Int?,
    selectedOptions: Set<Int>,
    onOptionSelected: (Int) -> Unit,
    onOptionToggled: (Int) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isMultiSelect = question.type == QuestionType.MULTI

    SurveyQuestionLayout(
        questionNumber = question.displayOrder,
        totalQuestions = totalQuestions,
        questionTitle = question.questionText,
        imageUrl = question.imageUrl,
        questionNote = if (isMultiSelect) "(최대 ${question.maxSelect}개)" else null,
        onBackClick = onBackClick,
        content = {
            if (isMultiSelect) {
                MultiSelectContent(
                    options = question.options,
                    selectedOptions = selectedOptions,
                    maxSelect = question.maxSelect,
                    onOptionToggled = onOptionToggled,
                    onNextClick = onNextClick,
                    isNextEnabled = selectedOptions.isNotEmpty()
                )
            } else {
                SingleSelectContent(
                    options = question.options,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun SingleSelectContent(
    options: List<QuestionOption>,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        options.forEach { option ->
            SurveyOptionButton(
                text = "${option.icon} ${option.text}",
                isSelected = selectedOption == option.code,
                onClick = { onOptionSelected(option.code - 1) }
            )
        }
    }
}

@Composable
private fun MultiSelectContent(
    options: List<QuestionOption>,
    selectedOptions: Set<Int>,
    maxSelect: Int,
    onOptionToggled: (Int) -> Unit,
    onNextClick: () -> Unit,
    isNextEnabled: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            options.chunked(2).forEach { rowOptions ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowOptions.forEach { option ->
                        val index = options.indexOf(option)
                        SurveyOptionButton(
                            text = "${option.icon} ${option.text}",
                            isSelected = selectedOptions.contains(option.code),
                            onClick = { onOptionToggled(index) },
                            modifier = Modifier.weight(1f),
                            maxLines = 2
                        )
                    }
                    if (rowOptions.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        if (selectedOptions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onNextClick,
                enabled = isNextEnabled,
                modifier = Modifier
                    .width(343.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DduruTheme.colors.primary,
                    contentColor = DduruTheme.colors.gray0,
                    disabledContainerColor = DduruTheme.colors.gray200,
                    disabledContentColor = DduruTheme.colors.gray500
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "다음 (${selectedOptions.size}/$maxSelect)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )
            }
        }
    }
}

private val previewSingleQuestion = Question(
    id = "rhythm_q1",
    displayOrder = 1,
    questionText = "다음 여행지로 출발~\n어떻게 가지?",
    imageUrl = null,
    type = QuestionType.SINGLE,
    required = true,
    minSelect = 1,
    maxSelect = 1,
    options = listOf(
        QuestionOption(code = 1, icon = "🚶", text = "여행은 낭만이지! 걷거나 버스"),
        QuestionOption(code = 2, icon = "🚕", text = "편한게 최고~ 택시 타는 거 어때?")
    )
)

private val previewThreeOptionQuestion = previewSingleQuestion.copy(
    id = "decision_q1",
    displayOrder = 8,
    questionText = "여행 계획 좀 세워볼까?",
    options = listOf(
        QuestionOption(code = 1, icon = "📝", text = "내가 다 짤게!"),
        QuestionOption(code = 2, icon = "🤝", text = "같이 정하자"),
        QuestionOption(code = 3, icon = "🙌", text = "난 따라갈게~")
    )
)

private val previewMultiQuestion = Question(
    id = "activity_tags",
    displayOrder = 7,
    questionText = "여행 가서 주로 뭐 하고 싶어?",
    imageUrl = null,
    type = QuestionType.MULTI,
    required = true,
    minSelect = 1,
    maxSelect = 3,
    options = listOf(
        QuestionOption(code = 1, icon = "🏛️", text = "관광"),
        QuestionOption(code = 2, icon = "⚽️", text = "관람"),
        QuestionOption(code = 3, icon = "🌳", text = "자연 탐방"),
        QuestionOption(code = 4, icon = "🍖", text = "먹방"),
        QuestionOption(code = 5, icon = "🛍️", text = "쇼핑"),
        QuestionOption(code = 6, icon = "🏖️", text = "휴양"),
        QuestionOption(code = 7, icon = "🏄🏻‍♂️", text = "액티비티"),
        QuestionOption(code = 8, icon = "💃", text = "페스티벌")
    )
)

@Composable
private fun SurveyQuestionScreenPreviewContent(
    question: Question,
    initialSelected: Set<Int> = emptySet()
) = DduruTheme {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var selectedOptions by remember { mutableStateOf(initialSelected) }

    SurveyQuestionScreen(
        question = question,
        totalQuestions = 11,
        selectedOption = selectedOption,
        selectedOptions = selectedOptions,
        onOptionSelected = { selectedOption = it + 1 },
        onOptionToggled = { index ->
            val code = question.options[index].code
            selectedOptions = if (code in selectedOptions) {
                selectedOptions - code
            } else if (selectedOptions.size < question.maxSelect) {
                selectedOptions + code
            } else {
                selectedOptions
            }
        },
        onNextClick = {},
        onBackClick = {}
    )
}

@Preview(name = "Q01 단일 선택 (2개)", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SurveyQuestionSinglePreview() {
    SurveyQuestionScreenPreviewContent(question = previewSingleQuestion)
}

@Preview(name = "Q08 단일 선택 (3개)", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SurveyQuestionThreeOptionPreview() {
    SurveyQuestionScreenPreviewContent(question = previewThreeOptionQuestion)
}

@Preview(name = "Q07 복수 선택", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SurveyQuestionMultiPreview() {
    SurveyQuestionScreenPreviewContent(question = previewMultiQuestion)
}

@Preview(name = "Q07 복수 선택 - 선택 후", showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun SurveyQuestionMultiSelectedPreview() {
    SurveyQuestionScreenPreviewContent(
        question = previewMultiQuestion,
        initialSelected = setOf(1, 4)
    )
}
