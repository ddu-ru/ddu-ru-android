package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class MySuperHostStatusResponse(
    val unusedTicketCount: Int = 0,
    val appliedPosts: List<SuperHostManagePostResponse> = emptyList(),
    val availablePosts: List<SuperHostManagePostResponse> = emptyList()
)

@Serializable
data class SuperHostManagePostResponse(
    val postId: Long,
    val title: String = "",
    val destination: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val recruitCount: Int = 0,
    val recruitCapacity: Int = 0,
    val photoUrl: String? = null,
    val viewCount: Int = 0,
    val superHostEndsAt: String? = null
)

@Serializable
data class SuperHostApplyResponse(
    val postId: Long,
    val ticketId: Long? = null,
    val startedAt: String = "",
    val endsAt: String = "",
    val unusedTicketCount: Int = 0
)
