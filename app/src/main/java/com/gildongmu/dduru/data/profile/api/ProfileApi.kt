package com.gildongmu.dduru.data.profile.api

import com.gildongmu.dduru.data.profile.model.ProfileRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileApi {
    @POST("api/v1/users/me/profile")
    suspend fun updateProfile(
        @Body request: ProfileRequest
    ): ApiResponse<Unit>
}
