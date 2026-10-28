package com.gildongmu.dduru.data.remote.dto.response.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyScheduleListResponse(
    val journeyId: Long,
    val startDate: String,
    val endDate: String,
    val days: List<JourneyScheduleDayResponse>
)

@Serializable
data class JourneyScheduleDayResponse(
    val day: Int,
    val date: String,
    val schedules: List<JourneyScheduleResponse>
)

@Serializable
data class JourneyScheduleResponse(
    val scheduleId: Long,
    val title: String,
    val category: String,
    val scheduleDate: String,
    val startTime: String?,
    val endTime: String?,
    val placeName: String?,
    val memo: String?
)
