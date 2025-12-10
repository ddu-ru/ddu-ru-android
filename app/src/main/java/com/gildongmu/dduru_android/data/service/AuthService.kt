package com.gildongmu.dduru_android.data.service

import com.gildongmu.dduru_android.data.model.request.LoginRequest
import com.gildongmu.dduru_android.data.model.request.RefreshTokenRequest
import com.gildongmu.dduru_android.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    // 카카오 로그인
    @POST("api/v1/auth/oauth2/kakao")
    suspend fun loginWithKakao(@Body request: LoginRequest): LoginResponse


    @POST("api/v1/auth/oauth2/google")
    suspend fun loginWithGoogle(@Body request: LoginRequest): LoginResponse
    // 토큰 갱신
    @POST("api/v1/auth/refresh")
    suspend fun refreshAccessToken(@Body request: RefreshTokenRequest): LoginResponse

    // 로그아웃
    @POST("api/v1/auth/logout")
    suspend fun logout(): Response<Unit>
}
