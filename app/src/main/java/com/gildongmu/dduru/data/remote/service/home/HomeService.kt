package com.gildongmu.dduru.data.remote.service.home

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeMateRecommendationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomePopularDestinationsResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameAgeTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSameDestinationTripResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSuperHostResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeUpcomingTripResponse
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

    @GET("api/v1/home/super-hosts")
    suspend fun getHomeSuperHosts(): ApiResponse<List<HomeSuperHostResponse>>

    @GET("api/v1/home/upcoming-trip")
    suspend fun getHomeUpcomingTrip(): ApiResponse<HomeUpcomingTripResponse>
}
