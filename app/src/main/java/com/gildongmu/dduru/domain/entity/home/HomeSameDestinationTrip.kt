package com.gildongmu.dduru.domain.entity.home

data class HomeSameDestinationTrip(
    val postId: Long,
    val title: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val thumbnailUrl: String
)
