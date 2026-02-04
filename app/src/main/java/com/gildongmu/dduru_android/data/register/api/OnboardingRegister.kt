package com.gildongmu.dduru_android.data.register.api

import com.gildongmu.dduru_android.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru_android.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

interface OnboardingRegisterApi {
    @PATCH("api/v1/me/profile")
    suspend fun initProfile(
        @Body request: OnboardingRegisterRequest
    ): ApiResponse<Unit>
}