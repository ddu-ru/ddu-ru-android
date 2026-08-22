package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchingPostService {
    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("cursor") cursor: Long? = null,
        @Query("cursorValue") cursorValue: Int? = null,
        @Query("size") size: Int? = null,
        @Query("keyword") keyword: String? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("preferredGender") preferredGender: String? = null,
        @Query("minAge") minAge: Int? = null,
        @Query("maxAge") maxAge: Int? = null,
        @Query("destinationId") destinationId: Long? = null,
        @Query("recruitmentStatus") recruitmentStatus: String? = null,
        @Query("companionType") companionType: String? = null,
        @Query("sort") sort: String? = null
    ): ApiResponse<JsonElement>
}
