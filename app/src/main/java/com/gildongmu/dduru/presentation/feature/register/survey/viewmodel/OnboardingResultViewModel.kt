package com.gildongmu.dduru.presentation.feature.register.survey.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.register.survey.state.SurveyResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingResultViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(SurveyResultUiState())
        private set

    init {
        loadSurveyResult()
    }

    private fun loadSurveyResult() {
        uiState = uiState.copy(
            avatarDescription = "모두가 편안해야\n내가 편해요!",
            avatarName = "포근포근형",
            imageUrl = "https://i.postimg.cc/MTD5tQ1Y/img-penguin-personallity.png",
            personality = "사람을 편안하게 해주며, 맛있는 것과 좋은 분위기만 있다면 행복한 미식 힐링러.",
            strength = "단체 일정 코디, 실패 없는 맛집 픽, 여행 중 쉼표 만들어주기.",
            tip = "활동적인 친구와 여행할 때는 '식사 후 자유시간'으로 완급을 조절하세요.",
            tags = listOf("따듯함", "세심함", "여행즐김러")
        )
    }
}
