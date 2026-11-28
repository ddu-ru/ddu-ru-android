package com.gildongmu.dduru.domain.entity.mypage

data class MyPagePost(
    val id: Long,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val createdAt: String? = null,
    val displayStatus: MyPagePostDisplayStatus? = null,
    val recruitCount: Int = 0,
    val recruitCapacity: Int = 0,
    val photoUrl: String? = null,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val preferredGender: String? = null,
    val companionType: String? = null,
    val authorNickname: String = "",
    val isAuthorSuperHost: Boolean = false
)

data class MyPagePostPage(
    val posts: List<MyPagePost>,
    val nextCursor: Long?,
    val hasNext: Boolean,
    val totalCount: Long? = null,
    val unusedSuperHostTicketCount: Int = 0
)

enum class MyPagePostDisplayStatus { RECRUITING, RECRUITMENT_CLOSED, TRAVEL_ENDED }

enum class MyPagePostFilter { ALL, RECRUITING, RECRUITMENT_CLOSED, TRAVEL_ENDED }

enum class MyPagePostSort(val label: String) {
    LATEST("최신순"),
    OLDEST("오래된순")
}
