package com.gildongmu.dduru.data.remote.dto.response.matching

import kotlinx.serialization.Serializable

@Serializable
data class MatchingPostListResponse(
    val posts: List<MatchingPostResponse> = emptyList(),
    val nextCursor: Long? = null,
    val nextCursorValue: Int? = null,
    val hasNext: Boolean = false,
    val size: Int = 0
)

@Serializable
data class MatchingPostResponse(
    val id: Long,
    val title: String,
    val status: String,
    val isFull: Boolean,
    val startDate: String,
    val endDate: String,
    val destination: String,
    val recruitCapacity: Int,
    val recruitCount: Int,
    val preferredGender: String,
    val companionType: String,
    val photoUrl: String? = null,
    val likeCount: Int = 0,
    val hasLiked: Boolean = false,
    val author: MatchingPostAuthorResponse
)

@Serializable
data class MatchingPostAuthorResponse(
    val nickname: String,
    val isSuperHost: Boolean = false
)
