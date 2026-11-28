package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class MyPageLikedPostListResponse(
    val posts: List<MyPageLikedPostResponse> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val size: Int = 0
)

@Serializable
data class MyPageLikedPostResponse(
    val id: Long,
    val title: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val recruitCount: Int = 0,
    val recruitCapacity: Int = 0,
    val preferredGender: String? = null,
    val companionType: String? = null,
    val photoUrl: String? = null,
    val author: MyPagePostAuthorResponse? = null
)

@Serializable
data class MyPagePostAuthorResponse(
    val nickname: String = "",
    val isSuperHost: Boolean = false
)

@Serializable
data class MyPagePostListResponse(
    val posts: List<MyPagePostResponse> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val size: Int = 0,
    val totalPostCount: Long = 0L,
    val unusedSuperHostTicketCount: Int = 0
)

@Serializable
data class MyPagePostResponse(
    val id: Long,
    val title: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val createdAt: String? = null,
    val recruitCount: Int = 0,
    val recruitCapacity: Int = 0,
    val photoUrl: String? = null,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val displayStatus: String? = null
)
