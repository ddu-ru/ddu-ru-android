package com.gildongmu.dduru.domain.entity.home

data class HomePopularDestinations(
    val updatedAt: String,
    val items: List<HomePopularDestination>
)

data class HomePopularDestination(
    val rank: Int,
    val destinationId: Long,
    val destinationName: String,
    val imageUrl: String,
    val availableTripCount: Int,
    val tags: List<String>
)
