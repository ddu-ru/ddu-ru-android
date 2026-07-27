package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeUpcomingTripResponse(
    val journeyId: Long = 0L,
    val title: String = "",
    val dDay: Int = 0,
    val startDate: String = "",
    val endDate: String = "",
    val currentMemberCount: Int = 0,
    val maxMemberCount: Int = 0,
    val pendingTaskCount: Int = 0
)
