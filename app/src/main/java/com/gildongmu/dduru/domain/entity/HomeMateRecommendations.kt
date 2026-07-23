package com.gildongmu.dduru.domain.entity

data class HomeMateRecommendations(
    val isAvailable: Boolean,
    val remainingFreeCount: Int,
    val recommendations: List<HomeMateRecommendation>
)

data class HomeMateRecommendation(
    val recommendationId: Long,
    val postId: Long,
    val matchPercentage: Int,
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val host: HomeMateHost,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val description: String,
    val tags: List<String>
)

data class HomeMateHost(
    val nickname: String,
    val profileImageInfo: HomeProfileImageInfo?,
    val age: Int,
    val gender: String
)

data class HomeProfileImageInfo(
    val type: String,
    val url: String?,
    val bgColorId: Int?
)
