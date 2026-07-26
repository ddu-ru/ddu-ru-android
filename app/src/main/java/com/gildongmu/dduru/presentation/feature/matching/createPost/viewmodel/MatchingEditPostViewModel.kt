package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostMockData
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiAction
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingEditPostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.reduce
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MatchingEditPostViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        MatchingEditPostUiState(popularTags = MatchingCreatePostMockData.popularTags)
    )
    val uiState: StateFlow<MatchingEditPostUiState> = _uiState.asStateFlow()

    fun onDateSectionAction(action: DateSectionUiAction) {
        _uiState.update { currentState ->
            currentState.copy(
                dateSectionUiState = currentState.dateSectionUiState.reduce(action)
            )
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

    fun onTagInputChange(tagInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                    tagInput = tagInput,
                    tagValidationMessage = null
                )
            )
        }
    }

    fun onAddTag() {
        _uiState.update { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.addTag()
            )
        }
    }

    fun onRemoveTag(tag: String) {
        _uiState.update { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.removeTag(tag)
            )
        }
    }
}
