package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyPostNoticeRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyPostRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyPostUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostNoticeResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JourneyPostService {
    @GET("api/v1/journeys/{journeyId}/posts")
    suspend fun getJourneyPosts(
        @Path("journeyId") journeyId: Long,
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int? = null
    ): ApiResponse<JourneyPostListResponse>

    @POST("api/v1/journeys/{journeyId}/posts")
    suspend fun createJourneyPost(
        @Path("journeyId") journeyId: Long,
        @Body request: JourneyPostRequest
    ): ApiResponse<JourneyPostResponse>

    @GET("api/v1/journeys/{journeyId}/posts/{journeyPostId}")
    suspend fun getJourneyPost(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long
    ): ApiResponse<JourneyPostResponse>

    @PATCH("api/v1/journeys/{journeyId}/posts/{journeyPostId}")
    suspend fun updateJourneyPost(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long,
        @Body request: JourneyPostUpdateRequest
    ): ApiResponse<JourneyPostResponse>

    @DELETE("api/v1/journeys/{journeyId}/posts/{journeyPostId}")
    suspend fun deleteJourneyPost(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long
    ): Response<Unit>

    @PATCH("api/v1/journeys/{journeyId}/posts/{journeyPostId}/notice")
    suspend fun updateJourneyPostNotice(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long,
        @Body request: JourneyPostNoticeRequest
    ): ApiResponse<JourneyPostNoticeResponse>
}
