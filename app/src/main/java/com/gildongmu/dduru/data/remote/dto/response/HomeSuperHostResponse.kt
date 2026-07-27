package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeSuperHostResponse(
    val postId: Long = 0L,
    val status: String = "",
    val title: String = "",
    val location: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val currentMemberCount: Int = 0,
    val maxMemberCount: Int = 0,
    val tags: List<String> = emptyList(),
    val host: HomeSuperHostHostResponse = HomeSuperHostHostResponse(),
    val viewCount: Int = 0,
    val thumbnailUrl: String = "",
    val hasLiked: Boolean = false
)

@Serializable
data class HomeSuperHostHostResponse(
    val nickname: String = "",
    val profileImageInfo: HomeProfileImageInfoResponse? = null,
    val age: Int = 0,
    val gender: String = ""
)
