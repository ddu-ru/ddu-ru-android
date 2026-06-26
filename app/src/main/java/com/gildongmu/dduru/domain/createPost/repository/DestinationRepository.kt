package com.gildongmu.dduru.domain.createPost.repository

import com.gildongmu.dduru.domain.createPost.entity.PopularDestination

interface DestinationRepository {
    suspend fun getPopularDestinations(): Result<List<PopularDestination>>
    suspend fun searchDestinations(keyword: String): Result<List<PopularDestination>>
}
