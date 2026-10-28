package com.gildongmu.dduru.domain.journey.entity

data class JourneyScheduleList(
    val journeyId: Long,
    val startDate: String,
    val endDate: String,
    val days: List<JourneyScheduleDay>
)

data class JourneyScheduleDay(
    val day: Int,
    val date: String,
    val schedules: List<JourneySchedule>
)

data class JourneySchedule(
    val scheduleId: Long,
    val title: String,
    val category: String,
    val scheduleDate: String,
    val startTime: String?,
    val endTime: String?,
    val placeName: String?,
    val memo: String?
)
