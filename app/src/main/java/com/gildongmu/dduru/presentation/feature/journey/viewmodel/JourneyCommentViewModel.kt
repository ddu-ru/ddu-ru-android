package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.journey.entity.JourneyComment
import com.gildongmu.dduru.domain.journey.entity.JourneyPost
import com.gildongmu.dduru.domain.journey.repository.JourneyCommentRepository
import com.gildongmu.dduru.domain.journey.repository.JourneyPostRepository
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentItemUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JourneyCommentViewModel @Inject constructor(
    private val journeyPostRepository: JourneyPostRepository,
    private val journeyCommentRepository: JourneyCommentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<JourneyCommentUiState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var journeyId: Long? = null
    private var postId: Long? = null

    fun loadPostDetail(journeyId: Long, postId: Long) {
        if (this.journeyId == journeyId && this.postId == postId && _uiState.value is UiState.Success) return

        this.journeyId = journeyId
        this.postId = postId
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val postResult = journeyPostRepository.getJourneyPost(journeyId, postId)
            val commentsResult = journeyCommentRepository.getJourneyComments(journeyId, postId)
            if (postResult.isFailure || commentsResult.isFailure) {
                _uiState.value = UiState.Failure
                return@launch
            }
            val post = postResult.getOrThrow()
            val comments = commentsResult.getOrThrow().comments
            _uiState.value = UiState.Success(
                JourneyCommentUiState(
                    post = post.toUiState(),
                    comments = comments.map(JourneyComment::toUiState)
                )
            )
        }
    }

    fun onCommentTextChange(commentText: String) {
        updateData { it.copy(commentText = commentText) }
    }

    fun onSendClick() {
        val currentJourneyId = journeyId ?: return
        val currentPostId = postId ?: return
        val content = (_uiState.value as? UiState.Success)?.data?.commentText?.trim().orEmpty()
        if (content.isEmpty()) return

        viewModelScope.launch {
            journeyCommentRepository.createJourneyComment(
                journeyId = currentJourneyId,
                journeyPostId = currentPostId,
                command = com.gildongmu.dduru.domain.journey.entity.JourneyCommentCommand(content)
            ).onSuccess { comment ->
                updateData { data ->
                    data.copy(
                        comments = data.comments + comment.toUiState(),
                        commentText = ""
                    )
                }
            }
        }
    }

    fun onReplyClick(comment: JourneyCommentItemUiState) {
        updateData { it.copy(commentText = "@${comment.username} ") }
    }

    fun onPinClick(commentId: Long) {
        updateData { currentState ->
            currentState.copy(
                post = currentState.post?.togglePin(commentId),
                comments = currentState.comments.map { it.togglePin(commentId) }
            )
        }
    }

    private fun updateData(transform: (JourneyCommentUiState) -> JourneyCommentUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }

    private fun JourneyCommentItemUiState.togglePin(commentId: Long): JourneyCommentItemUiState =
        if (id == commentId) copy(isPinned = !isPinned) else this
}

private fun JourneyPost.toUiState() = JourneyCommentItemUiState(
    id = journeyPostId,
    profileImageUrl = author.profileImage.url,
    username = author.nickname,
    createdAt = createdAt,
    content = content,
    isPinned = false
)

private fun JourneyComment.toUiState() = JourneyCommentItemUiState(
    id = commentId,
    profileImageUrl = author.profileImage.url,
    username = author.nickname,
    createdAt = createdAt,
    content = content
)
