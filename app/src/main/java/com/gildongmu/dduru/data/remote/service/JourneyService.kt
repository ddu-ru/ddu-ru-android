package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyMemberRoleUpdateRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyScheduleRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyUpdateRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyDetailResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyMemberResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyMemberRoleUpdateResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyScheduleListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyUpdateResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.MyJourneyListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JourneyService {
    @GET("api/v1/journeys/{journeyId}")
    suspend fun getJourneyDetail(
        @Path("journeyId") journeyId: Long
    ): ApiResponse<JourneyDetailResponse>

    @PATCH("api/v1/journeys/{journeyId}")
    suspend fun updateJourney(
        @Path("journeyId") journeyId: Long,
        @Body request: JourneyUpdateRequest
    ): ApiResponse<JourneyUpdateResponse>

    @GET("api/v1/journeys/{journeyId}/schedules")
    suspend fun getJourneySchedules(
        @Path("journeyId") journeyId: Long
    ): ApiResponse<JourneyScheduleListResponse>

    @POST("api/v1/journeys/{journeyId}/schedules")
    suspend fun createJourneySchedule(
        @Path("journeyId") journeyId: Long,
        @Body request: JourneyScheduleRequest
    ): Response<Unit>

    @GET("api/v1/journeys/{journeyId}/members")
    suspend fun getJourneyMembers(
        @Path("journeyId") journeyId: Long
    ): ApiResponse<List<JourneyMemberResponse>>

    @DELETE("api/v1/journeys/{journeyId}/members/{memberUserId}")
    suspend fun removeJourneyMember(
        @Path("journeyId") journeyId: Long,
        @Path("memberUserId") memberUserId: Long
    ): Response<Unit>

    @PUT("api/v1/journeys/{journeyId}/members/{memberUserId}/roles")
    suspend fun replaceJourneyMemberRoles(
        @Path("journeyId") journeyId: Long,
        @Path("memberUserId") memberUserId: Long,
        @Body request: JourneyMemberRoleUpdateRequest
    ): ApiResponse<JourneyMemberRoleUpdateResponse>

    @GET("api/v1/users/me/journeys")
    suspend fun getMyJourneys(): ApiResponse<MyJourneyListResponse>
}
