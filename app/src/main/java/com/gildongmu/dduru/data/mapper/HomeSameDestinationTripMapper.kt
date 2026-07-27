package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeSameDestinationTripResponse
import com.gildongmu.dduru.domain.entity.HomeSameDestinationTrip

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
