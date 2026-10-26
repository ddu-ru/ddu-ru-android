package com.gildongmu.dduru.data.remote.dto.response.post

import kotlinx.serialization.Serializable

@Serializable
data class PostListResponse(
    val posts: List<PostSummaryResponse> = emptyList(),
    val nextCursor: Long? = null,
    val nextCursorValue: Int? = null,
    val hasNext: Boolean = false
)

@Serializable
data class PostSummaryResponse(
    val id: Long,
    val title: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val recruitCapacity: Int = 0,
    val recruitCount: Int = 0,
    val preferredGender: String = "U",
    val companionType: String = "FULL",
    val photoUrl: String? = null,
    val hasLiked: Boolean = false,
    val author: PostSummaryAuthorResponse = PostSummaryAuthorResponse()
)

@Serializable
data class PostSummaryAuthorResponse(
    val nickname: String = "",
    val isSuperHost: Boolean = false
)

@Serializable
data class PostDetailResponse(
    val id: Long,
    val title: String = "",
    val content: String = "",
    val status: String = "OPEN",
    val isFull: Boolean = false,
    val startDate: String = "",
    val endDate: String = "",
    val destination: String = "",
    val recruitCapacity: Int = 0,
    val recruitCount: Int = 0,
    val preferredGender: String = "U",
    val photoUrl: String? = null,
    val tags: List<String> = emptyList(),
    val likeCount: Int = 0,
    val author: PostAuthorResponse = PostAuthorResponse(),
    val isOwner: Boolean = false,
    val hasLiked: Boolean = false,
    val myParticipationStatus: String = "NONE",
    val tripDurationText: String = "",
    val recruitDeadlineDDay: String = "",
    val companionType: String = "FULL"
)

@Serializable
data class PostAuthorResponse(
    val userId: Long = 0L,
    val nickname: String = "",
    val gender: String = "U",
    val ageGroup: String? = null,
    val isSuperHost: Boolean = false
)

@Serializable
data class ParticipationRequest(val message: String = "")
