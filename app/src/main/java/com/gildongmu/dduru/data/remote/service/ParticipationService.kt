package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationApproveResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationContactResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ParticipationService {

    @GET("api/v1/participations")
    suspend fun getParticipations(): ApiResponse<List<ParticipationRetrieveResponse>>

    @POST("api/v1/participations/{participationId}/contact")
    suspend fun contactParticipation(
        @Path("participationId") participationId: Long
    ): ApiResponse<ParticipationContactResponse>

    @PATCH("api/v1/participations/{participationId}/approve")
    suspend fun approveParticipation(
        @Path("participationId") participationId: Long
    ): ApiResponse<ParticipationApproveResponse>

    @PATCH("api/v1/participations/{participationId}/reject")
    suspend fun rejectParticipation(
        @Path("participationId") participationId: Long
    ): Response<Unit>

    @GET("api/v1/users/me/participations")
    suspend fun getMyParticipations(): ApiResponse<List<MyParticipationResponse>>

    @DELETE("api/v1/participations/{participationId}")
    suspend fun cancelParticipation(
        @Path("participationId") participationId: Long
    ): Response<Unit>
}
