package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class BlockedUserListResponse(
    val users: List<BlockedUserResponse> = emptyList(),
    val nextCursor: Long? = null,
    val hasNext: Boolean = false
)

@Serializable
data class BlockedUserResponse(
    val userId: Long,
    val nickname: String = "",
    val bio: String? = null,
    val profileImage: MyProfileImageResponse? = null
)
