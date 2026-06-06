package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MatchingCreatePostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MatchingCreatePostUiState())
    val uiState: StateFlow<MatchingCreatePostUiState> = _uiState.asStateFlow()

    fun onDismissClick() {
        // TODO: 화면 닫기 이벤트 처리
    }

    fun onBackClick() {
        _uiState.update { currentState ->
            if (currentState.currentStep > 1) {
                currentState.copy(currentStep = currentState.currentStep - 1)
            } else {
                currentState
            }
        }
    }

    fun onNextClick() {
        _uiState.update { currentState ->
            if (currentState.currentStep < MatchingCreatePostUiState.MAX_STEP) {
                currentState.copy(currentStep = currentState.currentStep + 1)
            } else {
                // TODO: 여행방 생성 API 호출
                currentState
            }
        }
    }

    fun onAgeSectionUiStateChange(ageSectionUiState: AgeSectionUiState) {
        _uiState.update { currentState ->
            currentState.copy(ageSectionUiState = ageSectionUiState)
        }
    }
}
