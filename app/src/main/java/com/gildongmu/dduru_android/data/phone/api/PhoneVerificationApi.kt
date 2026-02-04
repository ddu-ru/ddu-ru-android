package com.gildongmu.dduru_android.data.phone.api

import com.gildongmu.dduru_android.data.phone.model.VerificationConfirmRequest
import com.gildongmu.dduru_android.data.phone.model.VerificationConfirmResponse
import com.gildongmu.dduru_android.data.phone.model.VerificationSendRequest
import com.gildongmu.dduru_android.data.phone.model.VerificationSendResponse
import com.gildongmu.dduru_android.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PhoneVerificationApi {

    @POST("api/v1/verifications")
    suspend fun sendVerificationCode(
        @Body request: VerificationSendRequest
    ): ApiResponse<VerificationSendResponse>

    @PATCH("api/v1/verifications")
    suspend fun confirmVerificationCode(
        @Body request: VerificationConfirmRequest
    ): ApiResponse<VerificationConfirmResponse>
}