package com.gildongmu.dduru_android.presentation.feature.register.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.data.model.survey.SurveyState
import com.gildongmu.dduru_android.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse
// import com.gildongmu.dduru_android.domain.repository.SurveyRepository // 👈 나중에 리포지토리 만들면 주석 해제
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI 상태
sealed class SurveyUiState {
    object Idle : SurveyUiState()
    object Loading : SurveyUiState()
    data class Success(val result: SurveyResponse) : SurveyUiState()
    data class Error(val message: String) : SurveyUiState()
}

@HiltViewModel
class SurveyViewModel @Inject constructor(
    // private val surveyRepository: SurveyRepository // 👈 나중에 주석 해제
) : ViewModel() {

    // 설문 답변 상태 (Q1~Q11 데이터 저장소)
    private val _surveyState = MutableStateFlow(SurveyState())
    val surveyState: StateFlow<SurveyState> = _surveyState.asStateFlow()

    // UI 상태 (로딩바, 에러메시지 등)
    private val _uiState = MutableStateFlow<SurveyUiState>(SurveyUiState.Idle)
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    // 현재 질문 번호 (1~11)
    private val _currentQuestion = MutableStateFlow(0)
    val currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    // Q1-Q6, Q8-Q11 답변 업데이트
    fun updateAnswer(questionNumber: Int, answer: Int) {
        _surveyState.value = when (questionNumber) {
            1 -> _surveyState.value.copy(q1 = answer)
            2 -> _surveyState.value.copy(q2 = answer)
            3 -> _surveyState.value.copy(q3 = answer)
            4 -> _surveyState.value.copy(q4 = answer)
            5 -> _surveyState.value.copy(q5 = answer)
            6 -> _surveyState.value.copy(q6 = answer)
            8 -> _surveyState.value.copy(q8 = answer)
            9 -> _surveyState.value.copy(q9 = answer)
            10 -> _surveyState.value.copy(q10 = answer)
            11 -> _surveyState.value.copy(q11 = answer)
            else -> _surveyState.value
        }
    }

    // Q7 다중 선택 답변 업데이트
    fun toggleQ7Answer(answer: Int) {
        val currentAnswers = _surveyState.value.q7.toMutableSet()
        if (currentAnswers.contains(answer)) {
            currentAnswers.remove(answer)
        } else {
            if (currentAnswers.size < 3) {
                currentAnswers.add(answer)
            }
        }
        _surveyState.value = _surveyState.value.copy(q7 = currentAnswers)
    }

    // 다음 질문으로 이동
    fun nextQuestion() {
        if (_currentQuestion.value < 11) {
            _currentQuestion.value += 1
        }
    }

    // 이전 질문으로 이동
    fun previousQuestion() {
        if (_currentQuestion.value > 1) {
            _currentQuestion.value -= 1
        }
    }

    // 설문 제출 (API 통신)
    fun submitSurvey() {
        val request = _surveyState.value.toRequest()

        if (request == null) {
            _uiState.value = SurveyUiState.Error("모든 질문에 답변해주세요.")
            return
        }

        viewModelScope.launch {
            _uiState.value = SurveyUiState.Loading
            kotlinx.coroutines.delay(1000)
            try {
                // TODO: 리포지토리 연결 전 임시 성공 처리
                // 실제 코드:
                // val result = surveyRepository.submitSurvey(request)
                // onSuccess / onFailure 처리...

                // 임시로 성공했다고 가정 (테스트용)
                val fakeResponse = com.gildongmu.dduru_android.data.remote.dto.response.SurveyResponse(
                    r = 0.0, w = 0.0, s = 0.0, p = 0.0,
                    avatarCode = 1, avatarType = "펭귄", avatarName = "뚜루",
                    avatarDescription = "설명", personality = "성격", strength = "강점", tip = "팁", tags = listOf()
                )
                _uiState.value = SurveyUiState.Success(fakeResponse)
                // _uiState.value = SurveyUiState.Success(SurveyResponse(...))

            } catch (e: Exception) {
                _uiState.value = SurveyUiState.Error(e.message ?: "알 수 없는 오류")
            }
        }

    }
}