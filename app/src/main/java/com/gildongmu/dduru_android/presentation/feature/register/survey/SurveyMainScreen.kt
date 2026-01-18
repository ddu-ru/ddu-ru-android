package com.gildongmu.dduru_android.presentation.feature.register.survey

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru_android.presentation.feature.register.survey.screen.* // Screen 파일들 임포트

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

    // 설문 완료 시 동작
//    /*LaunchedEffect(uiState) {
//        if (uiState is SurveyUiState.Success) {
//            onSurveyComplete()
//        }
//    }*/

    BackHandler(enabled = currentQuestion > 0) {
        viewModel.previousQuestion()
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 현재 질문 번호에 따라 화면 교체
        when (currentQuestion) {
            0 -> QuestionStart(
                onNextClick = { viewModel.nextQuestion() }, // 누르면 1번으로 이동
                onGuestClick = onBackPressed // "다음에 할게요" 누르면 나감
            )

            1 -> Question1Screen(
                selectedOption = surveyState.q1,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(1, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = onBackPressed
            )

            2 -> Question2Screen(
                selectedOption = surveyState.q2,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(2, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            3 -> Question3Screen(
                selectedOption = surveyState.q3,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(3, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            4 -> Question4Screen(
                selectedOption = surveyState.q4,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(4, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            5 -> Question5Screen(
                selectedOption = surveyState.q5,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(5, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            6 -> Question6Screen(
                selectedOption = surveyState.q6,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(6, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            7 -> Question7Screen(
                selectedOptions = surveyState.q7,
                isNextButtonEnabled = surveyState.q7.isNotEmpty(),
                onOptionToggled = { answer -> viewModel.toggleQ7Answer(answer) },
                onNextClick = { viewModel.nextQuestion() },
                onBackClick = { viewModel.previousQuestion() }
            )

            8 -> Question8Screen(
                selectedOption = surveyState.q8,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(8, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            9 -> Question9Screen(
                selectedOption = surveyState.q9,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(9, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            10 -> Question10Screen(
                selectedOption = surveyState.q10,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(10, answer)
                    viewModel.nextQuestion()
                },
                onBackClick = { viewModel.previousQuestion() }
            )

            11 -> Question11Screen(
                selectedOption = surveyState.q11,
                onOptionSelected = { answer ->
                    viewModel.updateAnswer(11, answer)
                    viewModel.submitSurvey()
                },
                onBackClick = { viewModel.previousQuestion() }
            )
        }

        // 로딩 상태일 때 로딩바 표시
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
        if (uiState is SurveyUiState.Success) {
            // 수정 포인트: .data를 지우고 result를 바로 data 변수에 담습니다.
            val data = (uiState as SurveyUiState.Success).result

            AlertDialog(
                onDismissRequest = { /* 비워둠 */ },
                title = { Text(text = "설문 결과") },
                text = {
                    Column {
                        Text(text = "당신의 여행 스타일은?", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = data.avatarName,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = data.avatarDescription)
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.resetUiState()
                            onSurveyComplete()
                        }
                    ) {
                        Text("확인")
                    }
                }
            )
        }

        // 에러 상태일 때 메시지 표시
        if (uiState is SurveyUiState.Error) {
            Text(
                text = (uiState as SurveyUiState.Error).message,
                modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp),
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
    }
}
