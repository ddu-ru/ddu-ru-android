package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomePopularDestinationResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomePopularDestinationsResponse
import com.gildongmu.dduru.domain.entity.home.HomePopularDestination
import com.gildongmu.dduru.domain.entity.home.HomePopularDestinations

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
