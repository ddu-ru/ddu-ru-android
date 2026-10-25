package com.gildongmu.dduru.presentation.feature.journey.viewmodel

import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentItemUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class JourneyCommentViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(JourneyCommentUiState())
    val uiState = _uiState.asStateFlow()

    fun setInitialContent(
        post: JourneyCommentItemUiState,
        comments: List<JourneyCommentItemUiState>
    ) {
        _uiState.update { it.copy(post = post, comments = comments) }
    }

    fun onCommentTextChange(commentText: String) {
        _uiState.update { it.copy(commentText = commentText) }
    }

    fun onSendClick() {
        _uiState.update { currentState ->
            val content = currentState.commentText.trim()
            if (content.isEmpty()) {
                currentState
            } else {
                val nextId = (listOfNotNull(currentState.post?.id) + currentState.comments.map { it.id })
                    .maxOrNull()
                    ?.plus(1L)
                    ?: 1L

                currentState.copy(
                    comments = currentState.comments + JourneyCommentItemUiState(
                        id = nextId,
                        username = "나",
                        createdAt = "방금",
                        content = content
                    ),
                    commentText = ""
                )
            }
        }
    }

    fun onReplyClick(comment: JourneyCommentItemUiState) {
        _uiState.update { it.copy(commentText = "@${comment.username} ") }
    }

    fun onPinClick(commentId: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                post = currentState.post?.togglePin(commentId),
                comments = currentState.comments.map { it.togglePin(commentId) }
            )
        }
    }

    private fun JourneyCommentItemUiState.togglePin(commentId: Long): JourneyCommentItemUiState =
        if (id == commentId) copy(isPinned = !isPinned) else this
}
