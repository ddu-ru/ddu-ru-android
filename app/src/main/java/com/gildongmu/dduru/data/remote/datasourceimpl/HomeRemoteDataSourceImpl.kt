package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.HomeRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSameAgeTripResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSameDestinationTripResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSuperHostResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeUpcomingTripResponse
import com.gildongmu.dduru.data.remote.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRemoteDataSource {
    override suspend fun getHomeInitialConfig(): ApiResponse<HomeInitialConfigResponse> {
        return homeService.getHomeInitialConfig()
    }

    override suspend fun getHomeMateRecommendations(): ApiResponse<HomeMateRecommendationsResponse> {
        return homeService.getHomeMateRecommendations()
    }

    override suspend fun getHomePopularDestinations(): ApiResponse<HomePopularDestinationsResponse> {
        return homeService.getHomePopularDestinations()
    }

    override suspend fun getHomeSameAgeTrips(): ApiResponse<List<HomeSameAgeTripResponse>> {
        return homeService.getHomeSameAgeTrips()
    }

    override suspend fun getHomeSameDestinationTrips(): ApiResponse<List<HomeSameDestinationTripResponse>> {
        return homeService.getHomeSameDestinationTrips()
    }

    override suspend fun getHomeSuperHosts(): ApiResponse<List<HomeSuperHostResponse>> {
        return homeService.getHomeSuperHosts()
    }

    override suspend fun getHomeUpcomingTrip(): ApiResponse<HomeUpcomingTripResponse> {
        return homeService.getHomeUpcomingTrip()
    }
}
