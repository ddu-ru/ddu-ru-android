package com.gildongmu.dduru.data.remote.dto.response.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyPostListResponse(
    val journeyId: Long,
    val posts: List<JourneyPostResponse>,
    val nextCursor: Long?,
    val hasNext: Boolean,
    val size: Int
)

@Serializable
data class JourneyPostResponse(
    val journeyPostId: Long,
    val author: JourneyAuthorResponse,
    val content: String,
    val imageUrls: List<String>,
    val isNotice: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val isAuthor: Boolean,
    val commentCount: Int
)

@Serializable
data class JourneyPostNoticeResponse(
    val journeyPostId: Long,
    val isNotice: Boolean
)
