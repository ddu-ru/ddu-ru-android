package com.gildongmu.dduru.data.register.api

import com.gildongmu.dduru.data.register.model.OnboardingStatusResponse
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import retrofit2.http.GET

interface OnboardingStatusApi {
    @GET("api/v1/users/me/onboarding/status")
    suspend fun getSurveyStatus(): ApiResponse<OnboardingStatusResponse>
}
