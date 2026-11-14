package com.gildongmu.dduru.presentation.feature.matching.createPost.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.domain.createPost.repository.CreatePostRepository
import com.gildongmu.dduru.domain.createPost.repository.PopularTagRepository
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.AgeSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiAction
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.DateSectionUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.MatchingEditPostUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.PostWritingUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentScreenUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.ThumbnailAndTagUiState
import com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.reduce
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.time.LocalDate
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MatchingEditPostViewModel @Inject constructor(
    private val postService: PostService,
    private val createPostRepository: CreatePostRepository,
    private val popularTagRepository: PopularTagRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingEditPostUiState>>(
        UiState.Success(
            MatchingEditPostUiState()
        )
    )
    val uiState: StateFlow<UiState<MatchingEditPostUiState>> = _uiState.asStateFlow()
    private var popularTags: List<String> = emptyList()

    init {
        viewModelScope.launch {
            popularTagRepository.getPopularTags().onSuccess { tags ->
                popularTags = tags
                updateUiState { it.copy(popularTags = tags) }
            }
        }
    }

    fun loadPost(postId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { postService.getPost(postId).data ?: error("게시글 정보를 찾을 수 없어요.") }
                .onSuccess { post ->
                    _uiState.value = UiState.Success(MatchingEditPostUiState(
                        postId = postId,
                        popularTags = popularTags,
                        dateSectionUiState = DateSectionUiState(
                            LocalDate.parse(post.startDate),
                            LocalDate.parse(post.endDate)
                        ),
                        recruitmentSectionUiState = RecruitmentScreenUiState(
                            recruitmentCount = com.gildongmu.dduru.presentation.feature.matching.createPost.uistate.RecruitmentCountUiState(post.recruitCapacity),
                            selectedCompanionType = MatchingCompanionType.entries.firstOrNull { it.code == post.companionType }
                        ),
                        ageSectionUiState = AgeSectionUiState(
                            selectedGender = MatchingGender.entries.firstOrNull { it.code == post.preferredGender },
                            isAgeIrrelevant = post.isAgeAny,
                            selectedStartAge = post.minAge,
                            selectedEndAge = post.maxAge
                        ),
                        postWritingUiState = PostWritingUiState(title = post.title, content = post.content),
                        thumbnailAndTagUiState = ThumbnailAndTagUiState(previewImageUrl = post.photoUrl, photoUrl = post.photoUrl, addedTags = post.tags)
                    ))
                }.onFailure { _uiState.value = UiState.Failure }
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
