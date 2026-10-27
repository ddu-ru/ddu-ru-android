package com.gildongmu.dduru.presentation.feature.register.survey

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.register.survey.screen.QuestionStart
import com.gildongmu.dduru.presentation.feature.register.survey.screen.SurveyQuestionScreen
import com.gildongmu.dduru.presentation.feature.register.survey.screen.SurveyResultScreen

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

    BackHandler(enabled = true) {
        when {
            uiState is SurveyUiState.Success -> { /* 결과 화면에서는 뒤로 막기 */ }
            currentQuestion > 0 -> viewModel.previousQuestion()
            else -> onBackPressed()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState is SurveyUiState.Error -> {
                DduruFailureScreen(
                    message = (uiState as SurveyUiState.Error).message,
                    onRetryClick = viewModel::retry
                )
            }

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
                DduruLoadingScreen()
            }

            questions.isEmpty() -> {
                DduruFailureScreen(message = "질문을 불러올 수 없습니다.")
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

                    val selectedOption: Int? = when (question.id) {
                        "rhythm_q1" -> surveyState.rhythmQ1
                        "rhythm_q2" -> surveyState.rhythmQ2
                        "rhythm_q3" -> surveyState.rhythmQ3
                        "consumption_q1" -> surveyState.consumptionQ1
                        "consumption_q2" -> surveyState.consumptionQ2
                        "consumption_q3" -> surveyState.consumptionQ3
                        "energy_q1" -> surveyState.energyQ1
                        "energy_q2" -> surveyState.energyQ2
                        "energy_q3" -> surveyState.energyQ3
                        "decision_q1" -> surveyState.decisionQ1
                        "decision_q2" -> surveyState.decisionQ2
                        "decision_q3" -> surveyState.decisionQ3
                        "record_style" -> surveyState.recordStyle
                        else -> null
                    }

                    val selectedOptions: Set<Int> = if (question.id == "activity_tags") {
                        surveyState.activityTags
                    } else {
                        emptySet()
                    }

                    SurveyQuestionScreen(
                        question = question,
                        totalQuestions = questions.size,
                        selectedOption = selectedOption,
                        selectedOptions = selectedOptions,
                        onOptionSelected = { answer ->
                            viewModel.updateAnswer(question.id, answer)
                            if (isLastQuestion) {
                                viewModel.submitSurvey()
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        onOptionToggled = { answer ->
                            if (question.id == "activity_tags") {
                                viewModel.toggleActivityTag(answer)
                            }
                        },
                        onNextClick = {
                            if (isLastQuestion) {
                                viewModel.submitSurvey()
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        onBackClick = {
                            Log.d("SurveyBack", "화면 뒤로 버튼 클릭 - Question: $currentQuestion")
                            if (currentQuestion > 0) {
                                viewModel.previousQuestion()
                            }
                        }
                    )
                }
            }
        }

        if (uiState is SurveyUiState.Loading) {
            DduruLoadingScreen()
        }
    }
}
