package com.gildongmu.dduru.data.remote.dto.response.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyCommentListResponse(
    val journeyPostId: Long,
    val commentCount: Int,
    val comments: List<JourneyCommentResponse>
)

@Serializable
data class JourneyCommentResponse(
    val commentId: Long,
    val journeyPostId: Long,
    val author: JourneyAuthorResponse,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val isAuthor: Boolean
)
