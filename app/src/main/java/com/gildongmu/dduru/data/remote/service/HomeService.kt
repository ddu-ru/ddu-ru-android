package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomePopularDestinationsResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSameAgeTripResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSameDestinationTripResponse
import retrofit2.http.GET

interface HomeService {
    @GET("api/v1/home")
    suspend fun getHomeInitialConfig(): ApiResponse<HomeInitialConfigResponse>

    @GET("api/v1/home/mate-recommendations")
    suspend fun getHomeMateRecommendations(): ApiResponse<HomeMateRecommendationsResponse>

    @GET("api/v1/home/popular-destinations")
    suspend fun getHomePopularDestinations(): ApiResponse<HomePopularDestinationsResponse>

    @GET("api/v1/home/same-age-trips")
    suspend fun getHomeSameAgeTrips(): ApiResponse<List<HomeSameAgeTripResponse>>

    @GET("api/v1/home/same-destination-trips")
    suspend fun getHomeSameDestinationTrips(): ApiResponse<List<HomeSameDestinationTripResponse>>
}
