package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationResponse
import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationsResponse
import com.gildongmu.dduru.domain.entity.HomePopularDestination
import com.gildongmu.dduru.domain.entity.HomePopularDestinations

fun HomePopularDestinationsResponse.toDomain(): HomePopularDestinations {
    return HomePopularDestinations(
        updatedAt = updatedAt,
        items = items.map { it.toDomain() }
    )
}

fun HomePopularDestinationResponse.toDomain(): HomePopularDestination {
    return HomePopularDestination(
        rank = rank,
        destinationId = destinationId,
        destinationName = destinationName,
        imageUrl = imageUrl,
        availableTripCount = availableTripCount,
        tags = tags
    )
}
