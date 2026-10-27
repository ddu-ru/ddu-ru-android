package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyScheduleRequest(
    val title: String,
    val category: String?,
    val dayOffset: Int,
    val startTime: String?,
    val endTime: String?,
    val placeName: String?,
    val memo: String?
)
