package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ParticipationService {

    @GET("api/v1/participations")
    suspend fun getParticipations(): ApiResponse<List<ParticipationRetrieveResponse>>

    @PATCH("api/v1/participations/{participationId}/approve")
    suspend fun approveParticipation(
        @Path("participationId") participationId: Long
    ): ApiResponse<Unit>

    @PATCH("api/v1/participations/{participationId}/reject")
    suspend fun rejectParticipation(
        @Path("participationId") participationId: Long
    ): ApiResponse<Unit>

    @GET("api/v1/users/me/participations")
    suspend fun getMyParticipations(): ApiResponse<List<MyParticipationResponse>>

    @DELETE("api/v1/participations/{participationId}")
    suspend fun cancelParticipation(
        @Path("participationId") participationId: Long
    ): ApiResponse<Unit>
}
