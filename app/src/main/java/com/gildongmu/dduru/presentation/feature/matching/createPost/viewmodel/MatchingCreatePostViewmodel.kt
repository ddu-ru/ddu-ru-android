package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchItemUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.LocationSearchUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingCreatePostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
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
                    searchQuery = location.city,
                    searchedLocations = currentState.locationSearchUiState.popularLocations
                ),
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                    regionName = location.city,
                    countryName = location.countryName
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
