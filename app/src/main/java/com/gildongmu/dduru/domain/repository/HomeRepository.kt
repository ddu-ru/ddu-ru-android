package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.HomePopularDestinations

interface HomeRepository {
    suspend fun getHomeInitialConfig(): Result<HomeInitialConfig>
    suspend fun getHomeMateRecommendations(): Result<HomeMateRecommendations>
    suspend fun getHomePopularDestinations(): Result<HomePopularDestinations>
}
