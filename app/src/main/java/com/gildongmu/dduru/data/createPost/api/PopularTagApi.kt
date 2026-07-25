package com.gildongmu.dduru.data.createPost.api

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.GET

interface PopularTagApi {
    @GET("/api/v1/tags/popular")
    suspend fun getPopularTags(): ApiResponse<List<String>>
}
