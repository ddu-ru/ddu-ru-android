package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.core.common.state.UiState
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

@HiltViewModel
class MatchingEditPostViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingEditPostUiState>>(
        UiState.Success(
            MatchingEditPostUiState(popularTags = MatchingCreatePostMockData.popularTags)
        )
    )
    val uiState: StateFlow<UiState<MatchingEditPostUiState>> = _uiState.asStateFlow()

    fun loadPost(postId: Long) {
        updateUiState { currentState ->
            currentState.copy(postId = postId)
        }
    }

    fun onDateSectionAction(action: DateSectionUiAction) {
        updateUiState { currentState ->
            currentState.copy(
                dateSectionUiState = currentState.dateSectionUiState.reduce(action)
            )
        }
    }

    fun onRecruitmentSectionUiStateChange(recruitmentSectionUiState: RecruitmentScreenUiState) {
        updateUiState { currentState ->
            currentState.copy(recruitmentSectionUiState = recruitmentSectionUiState)
        }
    }

    fun onAgeSectionUiStateChange(ageSectionUiState: AgeSectionUiState) {
        updateUiState { currentState ->
            currentState.copy(ageSectionUiState = ageSectionUiState)
        }
    }

    fun onPostWritingUiStateChange(postWritingUiState: PostWritingUiState) {
        updateUiState { currentState ->
            currentState.copy(postWritingUiState = postWritingUiState)
        }
    }

    fun onThumbnailAndTagUiStateChange(thumbnailAndTagUiState: ThumbnailAndTagUiState) {
        updateUiState { currentState ->
            currentState.copy(thumbnailAndTagUiState = thumbnailAndTagUiState)
        }
    }

    fun onThumbnailImageSelected(uri: Uri) {
        updateUiState { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.copy(
                    previewImageUrl = uri.toString()
                )
            )
        }
    }

    fun onTagInputChange(tagInput: String) {
        updateUiState { currentState ->
            val thumbnailState = currentState.thumbnailAndTagUiState
            currentState.copy(
                thumbnailAndTagUiState = thumbnailState.copy(
                    tagInput = tagInput,
                    tagValidationMessage = thumbnailState.validateTagInput(tagInput)
                )
            )
        }
    }

    fun onAddTag() {
        updateUiState { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.addTag()
            )
        }
    }

    fun onRemoveTag(tag: String) {
        updateUiState { currentState ->
            currentState.copy(
                thumbnailAndTagUiState = currentState.thumbnailAndTagUiState.removeTag(tag)
            )
        }
    }

    private fun updateUiState(
        reducer: (MatchingEditPostUiState) -> MatchingEditPostUiState
    ) {
        val currentState = (_uiState.value as? UiState.Success)?.data ?: return
        _uiState.value = UiState.Success(reducer(currentState))
    }
}
