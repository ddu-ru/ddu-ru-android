package com.gildongmu.dduru.presentation.feature.journey.state

data class JourneyCommentUiState(
    val post: JourneyCommentItemUiState? = null,
    val comments: List<JourneyCommentItemUiState> = emptyList(),
    val commentText: String = ""
)

data class JourneyCommentItemUiState(
    val id: Long,
    val profileImageUrl: String? = null,
    val username: String,
    val createdAt: String,
    val content: String,
    val isPinned: Boolean = false
)
