package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.FcmTokenDeleteRequest
import com.gildongmu.dduru.data.remote.dto.request.FcmTokenRegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface FcmTokenService {
    @POST("api/v1/fcm/token")
    suspend fun registerToken(
        @Body request: FcmTokenRegisterRequest
    ): Response<Unit>

    @HTTP(method = "DELETE", path = "api/v1/fcm/token", hasBody = true)
    suspend fun deleteToken(
        @Body request: FcmTokenDeleteRequest
    ): Response<Unit>
}
