package com.gildongmu.dduru.presentation.feature.matching.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.data.remote.service.PostService
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MatchingDetailViewModel @Inject constructor(
    private val postService: PostService
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MatchingDetailUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<MatchingDetailUiState>> = _uiState.asStateFlow()

    private var loadedPostId: Long? = null

    fun loadMatchingDetail(postId: Long) {
        if (loadedPostId == postId && _uiState.value is UiState.Success) return

        loadedPostId = postId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { postService.getPost(postId) }
                .onSuccess { response ->
                    val post = response.data ?: error("게시글 상세 응답이 비어있어요.")
                    _uiState.value = UiState.Success(
                        MatchingDetailUiState(
                            id = post.id,
                            title = post.title,
                            content = post.content,
                            status = if (post.status == "CLOSED") com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostRecruitStatus.CLOSED else com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostRecruitStatus.OPEN,
                            isFull = post.isFull,
                            startDate = post.startDate,
                            endDate = post.endDate,
                            destination = post.destination,
                            recruitCapacity = post.recruitCapacity,
                            recruitCount = post.recruitCount,
                            preferredGender = post.preferredGender,
                            photoUrl = post.photoUrl,
                            tags = post.tags,
                            likeCount = post.likeCount,
                            author = com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailAuthorUiState(
                                userId = post.author.userId,
                                nickname = post.author.nickname,
                                gender = post.author.gender,
                                ageGroup = post.author.ageGroup?.filter(Char::isDigit)?.toIntOrNull() ?: 0,
                                isSuperHost = post.author.isSuperHost
                            ),
                            isOwner = post.isOwner,
                            hasLiked = post.hasLiked,
                            tripDurationText = post.tripDurationText,
                            recruitDeadlineDDay = post.recruitDeadlineDDay,
                            companionType = post.companionType,
                            myParticipationStatus = when (post.myParticipationStatus) { "PENDING", "CONTACTING" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.PENDING; "APPROVED" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.APPROVED; "REJECTED" -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.REJECTED; else -> com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingPostParticipationStatus.NONE }
                        )
                    )
                }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onLikeClick() {
        val postId = (_uiState.value as? UiState.Success)?.data?.id ?: return
        viewModelScope.launch {
            postService.toggleLike(postId)
                .takeIf { it.status in 200..299 }
                ?: return@launch
            loadMatchingDetail(postId)
        }
    }
}
