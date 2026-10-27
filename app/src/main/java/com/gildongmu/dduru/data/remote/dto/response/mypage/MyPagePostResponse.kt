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
    val endDate: String = ""
)

@Serializable
data class MyPagePostListResponse(
    val posts: List<MyPagePostResponse> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false,
    val size: Int = 0,
    val totalPostCount: Long = 0L
)

@Serializable
data class MyPagePostResponse(
    val id: Long,
    val title: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val createdAt: String? = null,
    val displayStatus: String? = null
)
