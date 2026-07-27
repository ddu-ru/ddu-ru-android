package com.gildongmu.dduru.data.remote.dto.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeMateRecommendationsResponse(
    val isAvailable: Boolean = false,
    val remainingFreeCount: Int = 0,
    val recommendations: List<HomeMateRecommendationResponse> = emptyList()
)

@Serializable
data class HomeMateRecommendationResponse(
    val recommendationId: Long = 0L,
    val postId: Long = 0L,
    val matchPercentage: Int = 0,
    val title: String = "",
    val location: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val host: HomeMateHostResponse = HomeMateHostResponse(),
    val currentMemberCount: Int = 0,
    val maxMemberCount: Int = 0,
    val description: String = "",
    val tags: List<String> = emptyList()
)

@Serializable
data class HomeMateHostResponse(
    val nickname: String = "",
    val profileImageInfo: HomeProfileImageInfoResponse? = null,
    val age: Int = 0,
    val gender: String = ""
)

@Serializable
data class HomeProfileImageInfoResponse(
    val type: String = "",
    val url: String? = null,
    val bgColorId: Int? = null
)
