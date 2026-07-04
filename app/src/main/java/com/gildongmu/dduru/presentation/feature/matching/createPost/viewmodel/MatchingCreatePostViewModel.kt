package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostMockData
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostStep
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
    private val _uiState = MutableStateFlow(
        MatchingCreatePostUiState(
            popularTags = MatchingCreatePostMockData.popularTags
        )
    )
    val uiState: StateFlow<MatchingCreatePostUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MatchingCreatePostUiEvent>()
    val uiEvent: SharedFlow<MatchingCreatePostUiEvent> = _uiEvent.asSharedFlow()

    fun onDismissClick() {
        emitUiEvent(MatchingCreatePostUiEvent.Close)
    }

    fun onBackClick() {
        movePreviousStep()
    }

    fun onNextClick() {
        moveNextStep()
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
                ),
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                    regionName = location.city,
                    countryName = location.countryName
                )
            )
        }

        moveToStep(MatchingCreatePostStep.DATE)
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

    private fun moveToStep(step: MatchingCreatePostStep) {
        _uiState.update { currentState ->
            currentState.copy(currentStep = step)
        }
    }

    private fun moveNextStep() {
        val currentState = _uiState.value
        if (!currentState.isNextEnabled) return

        val nextStep = currentState.currentStep.nextOrNull()
        if (nextStep == null) {
            emitUiEvent(MatchingCreatePostUiEvent.Complete)
            return
        }

        moveToStep(nextStep)
    }

    private fun movePreviousStep() {
        val previousStep = _uiState.value.currentStep.previousOrNull() ?: return
        moveToStep(previousStep)
    }

    private fun emitUiEvent(event: MatchingCreatePostUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
