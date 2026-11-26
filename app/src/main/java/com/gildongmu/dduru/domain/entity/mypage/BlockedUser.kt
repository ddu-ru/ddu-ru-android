package com.gildongmu.dduru.domain.entity.mypage

data class BlockedUser(
    val userId: Long,
    val nickname: String,
    val bio: String?,
    val profileImageUrl: String?
)

data class BlockedUserPage(
    val users: List<BlockedUser>,
    val nextCursor: Long?,
    val hasNext: Boolean
)
