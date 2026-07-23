package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.HomeRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationsResponse
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
}
