package com.gildongmu.dduru_android.presentation.feature.register.survey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.data.model.survey.SurveyState
import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru_android.domain.usecase.survey.GetMySurveyResultUseCase
import com.gildongmu.dduru_android.domain.usecase.survey.SubmitSurveyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SurveyUiState {
    object Idle : SurveyUiState()
    object Loading : SurveyUiState()
    data class Success(val result: SurveyResponse) : SurveyUiState()
    data class Error(val message: String) : SurveyUiState()
}

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val submitSurveyUseCase: SubmitSurveyUseCase,
    private val getMySurveyResultUseCase: GetMySurveyResultUseCase
) : ViewModel() {

    private val _surveyState = MutableStateFlow(SurveyState())
    val surveyState: StateFlow<SurveyState> = _surveyState.asStateFlow()

    private val _uiState = MutableStateFlow<SurveyUiState>(SurveyUiState.Idle)
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _currentQuestion = MutableStateFlow(0)
    val currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    fun updateAnswer(questionNumber: Int, answer: Int) {
        val serverAnswer = answer + 1

        _surveyState.value = when (questionNumber) {
            1 -> _surveyState.value.copy(q1 = serverAnswer)
            2 -> _surveyState.value.copy(q2 = serverAnswer)
            3 -> _surveyState.value.copy(q3 = serverAnswer)
            4 -> _surveyState.value.copy(q4 = serverAnswer)
            5 -> _surveyState.value.copy(q5 = serverAnswer)
            6 -> _surveyState.value.copy(q6 = serverAnswer)
            8 -> _surveyState.value.copy(q8 = serverAnswer)
            9 -> _surveyState.value.copy(q9 = serverAnswer)
            10 -> _surveyState.value.copy(q10 = serverAnswer)
            11 -> _surveyState.value.copy(q11 = serverAnswer)
            else -> _surveyState.value
        }
    }
    fun toggleQ7Answer(answer: Int) {
        val serverAnswer = answer + 1

        val currentAnswers = _surveyState.value.q7.toMutableSet()

        // 변환된 값(serverAnswer)으로 체크하고 넣고 빼야 합니다.
        if (currentAnswers.contains(serverAnswer)) {
            currentAnswers.remove(serverAnswer)
        } else {
            if (currentAnswers.size < 3) {
                currentAnswers.add(serverAnswer)
            }
        }

        _surveyState.value = _surveyState.value.copy(q7 = currentAnswers)
    }

    fun nextQuestion() {
        if (_currentQuestion.value < 11) {
            _currentQuestion.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestion.value > 0) {
            _currentQuestion.value -= 1
        }
    }

    fun submitSurvey() {
        val state = _surveyState.value
        Log.d("SurveyDebug", "현재 상태: q1=${state.q1}, q2=${state.q2}, q3=${state.q3}, q4=${state.q4}, q5=${state.q5}, q6=${state.q6}, q7=${state.q7}, q8=${state.q8}, q9=${state.q9}, q10=${state.q10}, q11=${state.q11}")
        val request = _surveyState.value.toRequest()

        if (request == null) {
            Log.e("SurveyDebug", "유효성 검사 실패! 어떤 값이 null입니다.")
            _uiState.value = SurveyUiState.Error("모든 질문에 답변해주세요.")
            return
        }

        viewModelScope.launch {
            _uiState.value = SurveyUiState.Loading

            // UseCase 호출
            val result = submitSurveyUseCase(request)

            // Result 처리
            result.fold(
                onSuccess = { surveyResponse ->
                    _uiState.value = SurveyUiState.Success(surveyResponse)
                },
                onFailure = { error ->
                    _uiState.value = SurveyUiState.Error(
                        error.message ?: "설문 제출 중 오류가 발생했습니다."
                    )
                }
            )
        }
    }

    fun loadMySurveyResult() {
        viewModelScope.launch {
            _uiState.value = SurveyUiState.Loading

            val result = getMySurveyResultUseCase()

            result.fold(
                onSuccess = { surveyResponse ->
                    _uiState.value = SurveyUiState.Success(surveyResponse)
                },
                onFailure = { error ->
                    // 404
                    if (error.message?.contains("찾을 수 없습니다") == true) {
                        _uiState.value = SurveyUiState.Idle
                    } else {
                        _uiState.value = SurveyUiState.Error(
                            error.message ?: "설문 결과 조회 중 오류가 발생했습니다."
                        )
                    }
                }
            )
        }
    }

    fun resetUiState() {
        _uiState.value = SurveyUiState.Idle
    }
}
