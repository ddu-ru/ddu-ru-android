package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class HomePopularDestinationsResponse(
    val updatedAt: String = "",
    val items: List<HomePopularDestinationResponse> = emptyList()
)

@Serializable
data class HomePopularDestinationResponse(
    val rank: Int = 0,
    val destinationId: Long = 0L,
    val destinationName: String = "",
    val imageUrl: String = "",
    val availableTripCount: Int = 0,
    val tags: List<String> = emptyList()
)
