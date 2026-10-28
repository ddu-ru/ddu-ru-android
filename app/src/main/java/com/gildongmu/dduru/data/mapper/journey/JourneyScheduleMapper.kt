package com.gildongmu.dduru.data.mapper.journey

import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyScheduleDayResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyScheduleListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyScheduleResponse
import com.gildongmu.dduru.domain.journey.entity.JourneySchedule
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleDay
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleList

fun JourneyScheduleListResponse.toDomain() = JourneyScheduleList(
    journeyId = journeyId,
    startDate = startDate,
    endDate = endDate,
    days = days.map(JourneyScheduleDayResponse::toDomain)
)

private fun JourneyScheduleDayResponse.toDomain() = JourneyScheduleDay(
    day = day,
    date = date,
    schedules = schedules.map(JourneyScheduleResponse::toDomain)
)

private fun JourneyScheduleResponse.toDomain() = JourneySchedule(
    scheduleId = scheduleId,
    title = title,
    category = category,
    scheduleDate = scheduleDate,
    startTime = startTime,
    endTime = endTime,
    placeName = placeName,
    memo = memo
)
