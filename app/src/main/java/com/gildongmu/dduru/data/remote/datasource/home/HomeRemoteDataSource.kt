package com.gildongmu.dduru.data.remote.datasource.home

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomePopularDestinationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameAgeTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameDestinationTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSuperHostResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeUpcomingTripResponse

interface HomeRemoteDataSource {
    suspend fun getHomeInitialConfig(): ApiResponse<HomeInitialConfigResponse>
    suspend fun getHomeMateRecommendations(): ApiResponse<HomeMateRecommendationsResponse>
    suspend fun getHomePopularDestinations(): ApiResponse<HomePopularDestinationsResponse>
    suspend fun getHomeSameAgeTrips(): ApiResponse<List<HomeSameAgeTripResponse>>
    suspend fun getHomeSameDestinationTrips(): ApiResponse<List<HomeSameDestinationTripResponse>>
    suspend fun getHomeSuperHosts(): ApiResponse<List<HomeSuperHostResponse>>
    suspend fun getHomeUpcomingTrip(): ApiResponse<HomeUpcomingTripResponse>
}
