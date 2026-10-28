package com.gildongmu.dduru.domain.entity.mypage

data class MyPagePost(
    val id: Long,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val createdAt: String? = null,
    val displayStatus: MyPagePostDisplayStatus? = null
)

data class MyPagePostPage(
    val posts: List<MyPagePost>,
    val nextCursor: Long?,
    val hasNext: Boolean,
    val totalCount: Long? = null
)

enum class MyPagePostDisplayStatus { RECRUITING, RECRUITMENT_CLOSED, TRAVEL_ENDED }

enum class MyPagePostFilter { ALL, RECRUITING, RECRUITMENT_CLOSED, TRAVEL_ENDED }
