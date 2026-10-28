package com.gildongmu.dduru.domain.journey.entity

data class JourneyScheduleCommand(
    val title: String,
    val category: String?,
    val dayOffset: Int,
    val startTime: String?,
    val endTime: String?,
    val placeName: String?,
    val memo: String?
)
