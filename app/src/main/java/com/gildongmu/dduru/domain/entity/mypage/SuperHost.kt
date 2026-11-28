package com.gildongmu.dduru.domain.entity.mypage

import java.time.LocalDateTime

data class SuperHostStatus(
    val unusedTicketCount: Int,
    val appliedPosts: List<SuperHostPost>,
    val availablePosts: List<SuperHostPost>
)

data class SuperHostPost(
    val postId: Long,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val recruitCount: Int,
    val recruitCapacity: Int,
    val photoUrl: String?,
    val viewCount: Int,
    val superHostEndsAt: LocalDateTime?
)

data class SuperHostApplyResult(
    val postId: Long,
    val endsAt: LocalDateTime?,
    val unusedTicketCount: Int
)
