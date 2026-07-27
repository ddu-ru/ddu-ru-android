package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeSameDestinationTripResponse(
    val postId: Long = 0L,
    val title: String = "",
    val location: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val currentMemberCount: Int = 0,
    val maxMemberCount: Int = 0,
    val thumbnailUrl: String = "",
)
