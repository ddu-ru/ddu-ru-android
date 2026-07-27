package com.gildongmu.dduru.data.remote.dto.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeSameAgeTripResponse(
    val postId: Long = 0L,
    val title: String = "",
    val location: String = "",
    val startDate: String = "",
    val currentMemberCount: Int = 0,
    val maxMemberCount: Int = 0,
    val thumbnailUrl: String = "",
)
