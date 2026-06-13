package com.gildongmu.dduru.presentation.feature.register.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.model.SurveyState
import com.gildongmu.dduru.domain.entity.Question
import com.gildongmu.dduru.domain.entity.SurveyResult
import com.gildongmu.dduru.domain.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class SurveyUiState {
    object Idle : SurveyUiState()
    object Loading : SurveyUiState()
    data class Success(val result: SurveyResult) : SurveyUiState()
    data class Error(val message: String) : SurveyUiState()
}

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val tokenLocalDataSource: TokenLocalDataSource
) : ViewModel() {

    val accessToken: StateFlow<String?> = tokenLocalDataSource.accessToken
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _surveyState = MutableStateFlow(SurveyState())
    val surveyState: StateFlow<SurveyState> = _surveyState.asStateFlow()

    private val _uiState = MutableStateFlow<SurveyUiState>(SurveyUiState.Idle)
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _currentQuestion = MutableStateFlow(0)
    val currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

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
        val code = answer + 1
        _surveyState.value = when (questionId) {
            "rhythm_q1" -> _surveyState.value.copy(rhythmQ1 = code)
            "rhythm_q2" -> _surveyState.value.copy(rhythmQ2 = code)
            "rhythm_q3" -> _surveyState.value.copy(rhythmQ3 = code)
            "consumption_q1" -> _surveyState.value.copy(consumptionQ1 = code)
            "consumption_q2" -> _surveyState.value.copy(consumptionQ2 = code)
            "consumption_q3" -> _surveyState.value.copy(consumptionQ3 = code)
            "energy_q1" -> _surveyState.value.copy(energyQ1 = code)
            "energy_q2" -> _surveyState.value.copy(energyQ2 = code)
            "energy_q3" -> _surveyState.value.copy(energyQ3 = code)
            "decision_q1" -> _surveyState.value.copy(decisionQ1 = code)
            "decision_q2" -> _surveyState.value.copy(decisionQ2 = code)
            "decision_q3" -> _surveyState.value.copy(decisionQ3 = code)
            "record_style" -> _surveyState.value.copy(recordStyle = code)
            else -> _surveyState.value
        }
    }

    fun toggleActivityTag(answer: Int) {
        val code = answer + 1
        val current = _surveyState.value.activityTags.toMutableSet()
        if (current.contains(code)) {
            current.remove(code)
        } else {
            if (current.size < 3) {
                current.add(code)
            }
        }
        _surveyState.value = _surveyState.value.copy(activityTags = current)
    }

    fun toggleQ7Answer(answer: Int) = toggleActivityTag(answer)

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
        val answers = _surveyState.value.toAnswers()
        if (answers == null) {
            _uiState.value = SurveyUiState.Error("모든 질문에 답변해주세요.")
            return
        }

        viewModelScope.launch {
            _uiState.value = SurveyUiState.Loading

            surveyRepository.submitSurvey(answers).fold(
                onSuccess = { surveyResult ->
                    _uiState.value = SurveyUiState.Success(surveyResult)
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

            surveyRepository.getMySurveyResult().fold(
                onSuccess = { surveyResult ->
                    _uiState.value = SurveyUiState.Success(surveyResult)
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
