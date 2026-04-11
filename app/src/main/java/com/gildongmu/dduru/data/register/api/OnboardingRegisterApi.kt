package com.gildongmu.dduru.data.register.api

import com.gildongmu.dduru.data.register.model.OnboardingRegisterRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

interface OnboardingRegisterApi {
    @PATCH("api/v1/users/me/onboarding")
    suspend fun initProfile(
        @Body request: OnboardingRegisterRequest
    ): ApiResponse<Unit>
}
