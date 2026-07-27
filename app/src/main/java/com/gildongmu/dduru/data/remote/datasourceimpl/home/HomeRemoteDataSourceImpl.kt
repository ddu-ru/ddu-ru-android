package com.gildongmu.dduru.data.remote.datasourceimpl.home

import com.gildongmu.dduru.data.remote.datasource.home.HomeRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomePopularDestinationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameAgeTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameDestinationTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSuperHostResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeUpcomingTripResponse
import com.gildongmu.dduru.data.remote.service.home.HomeService
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
