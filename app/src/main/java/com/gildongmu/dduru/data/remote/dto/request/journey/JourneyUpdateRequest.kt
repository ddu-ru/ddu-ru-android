package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyUpdateRequest(
    val title: String,
    val photoUrl: String,
    val startDate: String,
    val endDate: String
)
