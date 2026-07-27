package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeUpcomingTripResponse
import com.gildongmu.dduru.domain.entity.HomeUpcomingTrip

fun HomeUpcomingTripResponse.toDomain(): HomeUpcomingTrip {
    return HomeUpcomingTrip(
        journeyId = journeyId,
        title = title,
        dDay = dDay,
        startDate = startDate,
        endDate = endDate,
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        pendingTaskCount = pendingTaskCount
    )
}
