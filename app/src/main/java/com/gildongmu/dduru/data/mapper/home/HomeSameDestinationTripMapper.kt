package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameDestinationTripResponse
import com.gildongmu.dduru.domain.entity.home.HomeSameDestinationTrip

fun HomeSameDestinationTripResponse.toDomain(): HomeSameDestinationTrip {
    return HomeSameDestinationTrip(
        postId = postId,
        title = title,
        location = location,
        startDate = startDate,
        endDate = endDate,
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        thumbnailUrl = thumbnailUrl
    )
}
