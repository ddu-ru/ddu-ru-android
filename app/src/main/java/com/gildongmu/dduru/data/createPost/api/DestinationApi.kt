package com.gildongmu.dduru.data.createPost.api

import com.gildongmu.dduru.data.createPost.model.PopularDestinationResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.GET

interface DestinationApi {
    @GET("/api/v1/destinations/popular")
    suspend fun getPopularDestinations(): ApiResponse<List<PopularDestinationResponse>>
}
