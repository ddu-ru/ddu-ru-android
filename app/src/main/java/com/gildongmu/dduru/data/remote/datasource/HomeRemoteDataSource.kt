package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationsResponse

interface HomeRemoteDataSource {
    suspend fun getHomeInitialConfig(): ApiResponse<HomeInitialConfigResponse>
    suspend fun getHomeMateRecommendations(): ApiResponse<HomeMateRecommendationsResponse>
    suspend fun getHomePopularDestinations(): ApiResponse<HomePopularDestinationsResponse>
}
