package com.gildongmu.dduru.presentation.feature.register.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.model.survey.SurveyState
import com.gildongmu.dduru.data.remote.dto.response.QuestionItem
import com.gildongmu.dduru.data.remote.dto.response.SurveyResponse
import com.gildongmu.dduru.domain.repository.SurveyRepository
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
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    private val _surveyState = MutableStateFlow(SurveyState())
    val surveyState: StateFlow<SurveyState> = _surveyState.asStateFlow()

    private val _uiState = MutableStateFlow<SurveyUiState>(SurveyUiState.Idle)
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _currentQuestion = MutableStateFlow(0)
    val currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    private val _questions = MutableStateFlow<List<QuestionItem>>(emptyList())
    val questions: StateFlow<List<QuestionItem>> = _questions.asStateFlow()

    private val _questionsLoading = MutableStateFlow(true)
    val questionsLoading: StateFlow<Boolean> = _questionsLoading.asStateFlow()

    init {
        loadSurveyQuestions()
    }

    private fun loadSurveyQuestions() {
        viewModelScope.launch {
            _questionsLoading.value = true
            val result = surveyRepository.getSurveyQuestions()
            result.fold(
                onSuccess = { response ->
                    _questions.value = response.questions
                    _questionsLoading.value = false
                },
                onFailure = { error ->
                    _uiState.value = SurveyUiState.Error(
                        error.message ?: "질문을 불러오는데 실패했습니다."
                    )
                    _questionsLoading.value = false
                }
            )
        }
    }

    fun updateAnswer(questionId: String, answer: Int) {
        val serverAnswer = answer + 1
        _surveyState.value = when (questionId) {
            "q1" -> _surveyState.value.copy(q1 = serverAnswer)
            "q2" -> _surveyState.value.copy(q2 = serverAnswer)
            "q3" -> _surveyState.value.copy(q3 = serverAnswer)
            "q4" -> _surveyState.value.copy(q4 = serverAnswer)
            "q5" -> _surveyState.value.copy(q5 = serverAnswer)
            "q6" -> _surveyState.value.copy(q6 = serverAnswer)
            "q8" -> _surveyState.value.copy(q8 = serverAnswer)
            "q9" -> _surveyState.value.copy(q9 = serverAnswer)
            "q10" -> _surveyState.value.copy(q10 = serverAnswer)
            "q11" -> _surveyState.value.copy(q11 = serverAnswer)
            else -> _surveyState.value
        }
    }

    fun toggleQ7Answer(answer: Int) {
        val serverAnswer = answer + 1
        val currentAnswers = _surveyState.value.q7.toMutableSet()

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
        val totalQuestions = _questions.value.size
        if (_currentQuestion.value < totalQuestions) {
            _currentQuestion.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestion.value > 0) {
            _currentQuestion.value -= 1
        }
    }

    fun submitSurvey() {
        val request = _surveyState.value.toRequest()
        if (request == null) {
            _uiState.value = SurveyUiState.Error("모든 질문에 답변해주세요.")
            return
        }

        viewModelScope.launch {
            _uiState.value = SurveyUiState.Loading

            val result = surveyRepository.submitSurvey(request)

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

            val result = surveyRepository.getMySurveyResult()

            result.fold(
                onSuccess = { surveyResponse ->
                    _uiState.value = SurveyUiState.Success(surveyResponse)
                },
                onFailure = { error ->
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
