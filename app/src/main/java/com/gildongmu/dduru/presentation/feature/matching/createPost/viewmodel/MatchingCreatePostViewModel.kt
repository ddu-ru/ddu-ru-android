package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiEvent
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingCreatePostViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MatchingCreatePostUiState())
    val uiState: StateFlow<MatchingCreatePostUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MatchingCreatePostUiEvent>()
    val uiEvent: SharedFlow<MatchingCreatePostUiEvent> = _uiEvent.asSharedFlow()

    fun onDismissClick() {
        viewModelScope.launch {
            _uiEvent.emit(MatchingCreatePostUiEvent.Close)
        }
    }

    fun onBackClick() {
        _uiState.update { currentState ->
            currentState.currentStep.previousOrNull()?.let { previousStep ->
                currentState.copy(currentStep = previousStep)
            } ?: currentState
        }
    }

    fun onNextClick() {
        val currentState = _uiState.value

        if (!currentState.isNextEnabled) return

        val nextStep = currentState.currentStep.nextOrNull()

        if (nextStep != null) {
            _uiState.update { latestState ->
                latestState.copy(currentStep = nextStep)
            }
            return
        }

        viewModelScope.launch {
            _uiEvent.emit(MatchingCreatePostUiEvent.Complete)
        }
    }

    fun onLocationSearchUiStateChange(locationSearchUiState: LocationSearchUiState) {
        _uiState.update { currentState ->
            currentState.copy(locationSearchUiState = locationSearchUiState)
        }
    }

    fun onLocationSelected(location: LocationSearchItemUiState) {
        _uiState.update { currentState ->
            currentState.copy(
                locationSearchUiState = currentState.locationSearchUiState.copy(
                    selectedLocation = location,
                    searchQuery = location.city
                )
            )
        }
    }

    fun onDateSectionUiStateChange(dateSectionUiState: DateSectionUiState) {
        _uiState.update { currentState ->
            currentState.copy(dateSectionUiState = dateSectionUiState)
        }
    }

    fun onRecruitmentSectionUiStateChange(recruitmentSectionUiState: RecruitmentScreenUiState) {
        _uiState.update { currentState ->
            currentState.copy(recruitmentSectionUiState = recruitmentSectionUiState)
        }
    }

    fun onAgeSectionUiStateChange(ageSectionUiState: AgeSectionUiState) {
        _uiState.update { currentState ->
            currentState.copy(ageSectionUiState = ageSectionUiState)
        }
    }

    fun onPostWritingUiStateChange(postWritingUiState: PostWritingUiState) {
        _uiState.update { currentState ->
            currentState.copy(postWritingUiState = postWritingUiState)
        }
    }

    fun onThumbnailAndTagUiStateChange(thumbnailAndTagUiState: ThumbnailAndTagUiState) {
        _uiState.update { currentState ->
            currentState.copy(thumbnailAndTagUiState = thumbnailAndTagUiState)
        }
    }
}
