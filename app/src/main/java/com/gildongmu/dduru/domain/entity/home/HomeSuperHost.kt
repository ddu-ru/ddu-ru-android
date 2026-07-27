package com.gildongmu.dduru.domain.entity.home

data class HomeSuperHost(
    val postId: Long,
    val status: String,
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val tags: List<String>,
    val host: HomeSuperHostHost,
    val viewCount: Int,
    val thumbnailUrl: String,
    val hasLiked: Boolean
)

data class HomeSuperHostHost(
    val nickname: String,
    val profileImageInfo: HomeProfileImageInfo?,
    val age: Int,
    val gender: String
)
