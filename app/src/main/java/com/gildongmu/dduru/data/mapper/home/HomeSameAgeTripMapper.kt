package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameAgeTripResponse
import com.gildongmu.dduru.domain.entity.home.HomeSameAgeTrip

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
