package com.gildongmu.dduru.data.remote.dto.response.mypage

import kotlinx.serialization.Serializable

@Serializable
data class MyPageOverviewResponse(
    val nickname: String = "",
    val bio: String? = null,
    val profileImage: MyProfileImageResponse? = null,
    val travelPreferences: TravelPreferenceResponse = TravelPreferenceResponse(),
    val travelStyleSummary: MyPageTravelStyleSummaryResponse? = null
)

@Serializable
data class MyPageTravelStyleSummaryResponse(
    val name: String = "",
    val description: String = "",
    val tags: List<String> = emptyList()
)
