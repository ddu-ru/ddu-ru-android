package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.SurveyResult
import com.gildongmu.dduru.domain.repository.SurveyRepository
import com.gildongmu.dduru.presentation.feature.mypage.model.TravelStyleTag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileEditTravelStyleUiState(
    val styleName: String = "",
    val bubbleMessage: String = "",
    val imageUrl: String = "",
    val tags: List<TravelStyleTag> = emptyList(),
    val descriptions: List<String> = emptyList(),
    val canRetake: Boolean = true
)

@HiltViewModel
class ProfileEditTravelStyleViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProfileEditTravelStyleUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileEditTravelStyleUiState>> = _uiState.asStateFlow()

    fun loadTravelStyle() {
        viewModelScope.launch {
            surveyRepository.getMySurveyResult()
                .onSuccess { _uiState.value = UiState.Success(it.toUiState()) }
                .onFailure { error ->
                    _uiState.value = if (error.message?.contains("찾을 수 없습니다") == true) {
                        UiState.Empty
                    } else {
                        UiState.Failure
                    }
                }
        }
    }

    fun onTagToggle(index: Int) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(
                data.copy(
                    tags = data.tags.mapIndexed { i, tag ->
                        if (i == index) tag.copy(isActive = !tag.isActive) else tag
                    }
                )
            )
        }
    }
}

private fun SurveyResult.toUiState() = ProfileEditTravelStyleUiState(
    styleName = characterName,
    bubbleMessage = speechBubbleText,
    imageUrl = imageUrl.orEmpty(),
    tags = tags.map { TravelStyleTag(emoji = "", label = it) },
    descriptions = listOf(descriptionLine1, descriptionLine2, descriptionLine3).filter { it.isNotBlank() },
    canRetake = canRetake
)
