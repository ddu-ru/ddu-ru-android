package com.gildongmu.dduru.domain.repository.home

import com.gildongmu.dduru.domain.entity.home.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.home.HomeMateRecommendations
import com.gildongmu.dduru.domain.entity.home.HomePopularDestinations
import com.gildongmu.dduru.domain.entity.home.HomeSameAgeTrip
import com.gildongmu.dduru.domain.entity.home.HomeSameDestinationTrip
import com.gildongmu.dduru.domain.entity.home.HomeSuperHost
import com.gildongmu.dduru.domain.entity.home.HomeUpcomingTrip

interface HomeRepository {
    suspend fun getHomeInitialConfig(): Result<HomeInitialConfig>
    suspend fun getHomeMateRecommendations(): Result<HomeMateRecommendations>
    suspend fun getHomePopularDestinations(): Result<HomePopularDestinations>
    suspend fun getHomeSameAgeTrips(): Result<List<HomeSameAgeTrip>>
    suspend fun getHomeSameDestinationTrips(): Result<List<HomeSameDestinationTrip>>
    suspend fun getHomeSuperHosts(): Result<List<HomeSuperHost>>
    suspend fun getHomeUpcomingTrip(): Result<HomeUpcomingTrip>
}
