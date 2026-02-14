package com.gildongmu.dduru.presentation.feature.register.survey

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.presentation.feature.register.survey.screen.*

@Composable
fun SurveyMainScreen(
    onSurveyComplete: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: SurveyViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val surveyState by viewModel.surveyState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val questionsLoading by viewModel.questionsLoading.collectAsState()

    BackHandler(enabled = currentQuestion > 0) {
        viewModel.previousQuestion()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState is SurveyUiState.Success -> {
                val data = (uiState as SurveyUiState.Success).result
                SurveyResultScreen(
                    result = data,
                    onConfirm = {
                        viewModel.resetUiState()
                        onSurveyComplete()
                    }
                )
            }

            questionsLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            questions.isEmpty() -> {
                Text(
                    text = "질문을 불러올 수 없습니다.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            currentQuestion == 0 -> {
                QuestionStart(
                    onNextClick = { viewModel.nextQuestion() },
                    onGuestClick = onBackPressed
                )
            }

            else -> {
                val questionIndex = currentQuestion - 1
                if (questionIndex < questions.size) {
                    val question = questions[questionIndex]
                    val isLastQuestion = currentQuestion == questions.size

                    SurveyQuestionScreen(
                        question = question,
                        selectedOption = when (question.id) {
                            "q1" -> surveyState.q1
                            "q2" -> surveyState.q2
                            "q3" -> surveyState.q3
                            "q4" -> surveyState.q4
                            "q5" -> surveyState.q5
                            "q6" -> surveyState.q6
                            "q8" -> surveyState.q8
                            "q9" -> surveyState.q9
                            "q10" -> surveyState.q10
                            "q11" -> surveyState.q11
                            else -> null
                        },
                        selectedOptions = if (question.id == "q7") surveyState.q7 else emptySet(),
                        onOptionSelected = { answer ->
                            viewModel.updateAnswer(question.id, answer)
                            if (isLastQuestion) {
                                viewModel.submitSurvey()
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        onOptionToggled = { answer ->
                            if (question.id == "q7") {
                                viewModel.toggleQ7Answer(answer)
                            }
                        },
                        onNextClick = {
                            if (isLastQuestion && question.id == "q7") {
                                viewModel.submitSurvey()
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        onBackClick = { viewModel.previousQuestion() }
                    )
                }
            }
        }

        if (uiState is SurveyUiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState is SurveyUiState.Error) {
            Text(
                text = (uiState as SurveyUiState.Error).message,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
    }
}
