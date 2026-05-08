package com.gildongmu.dduru.data.register.profile.api

import com.gildongmu.dduru.data.register.profile.model.ProfileRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ProfileApi {
    @PATCH("api/v1/users/me/profile")
    suspend fun updateProfile(
        @Body request: ProfileRequest
    ): Response<ApiResponse<Unit>>
}
