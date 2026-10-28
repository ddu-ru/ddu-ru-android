package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyCommentRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyCommentListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyCommentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface JourneyCommentService {
    @GET("api/v1/journeys/{journeyId}/posts/{journeyPostId}/comments")
    suspend fun getJourneyComments(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long
    ): ApiResponse<JourneyCommentListResponse>

    @POST("api/v1/journeys/{journeyId}/posts/{journeyPostId}/comments")
    suspend fun createJourneyComment(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long,
        @Body request: JourneyCommentRequest
    ): ApiResponse<JourneyCommentResponse>

    @PATCH("api/v1/journeys/{journeyId}/posts/{journeyPostId}/comments/{commentId}")
    suspend fun updateJourneyComment(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long,
        @Path("commentId") commentId: Long,
        @Body request: JourneyCommentRequest
    ): ApiResponse<JourneyCommentResponse>

    @DELETE("api/v1/journeys/{journeyId}/posts/{journeyPostId}/comments/{commentId}")
    suspend fun deleteJourneyComment(
        @Path("journeyId") journeyId: Long,
        @Path("journeyPostId") journeyPostId: Long,
        @Path("commentId") commentId: Long
    ): Response<Unit>
}
