package com.gildongmu.dduru.domain.entity.mypage

data class MyPageOverview(
    val nickname: String,
    val bio: String?,
    val profileImageType: String?,
    val profileImageUrl: String?,
    val bgColorId: Int?,
    val destinations: List<DestinationPreference>,
    val availableDates: List<AvailableDate>,
    val travelStyleSummary: TravelStyleSummary?
)

data class TravelStyleSummary(
    val name: String,
    val description: String,
    val tags: List<String>
)
