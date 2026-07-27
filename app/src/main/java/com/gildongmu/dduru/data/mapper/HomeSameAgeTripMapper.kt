package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeSameAgeTripResponse
import com.gildongmu.dduru.domain.entity.HomeSameAgeTrip

fun HomeSameAgeTripResponse.toDomain(): HomeSameAgeTrip {
    return HomeSameAgeTrip(
        postId = postId,
        title = title,
        location = location,
        startDate = startDate,
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        thumbnailUrl = thumbnailUrl
    )
}
