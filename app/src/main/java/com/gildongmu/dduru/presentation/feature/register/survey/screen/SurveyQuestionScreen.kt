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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        modifier = Modifier.width(343.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        options.forEachIndexed { index, option ->
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
            modifier = Modifier.width(343.dp),
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
                            outerVerticalPadding = 0.dp,
                            innerVerticalPadding = 20.dp,
                            innerHorizontalPadding = 8.dp,
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
