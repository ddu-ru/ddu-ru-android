package com.gildongmu.dduru_android.data.remote.service

import com.gildongmu.dduru_android.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru_android.data.remote.dto.request.RefreshTokenRequest
import com.gildongmu.dduru_android.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru_android.data.remote.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    // 카카오 로그인
    @POST("api/v1/auth/kakao")
    suspend fun loginWithKakao(@Body request: LoginRequest): ApiResponse<LoginResponse>


    @POST("api/v1/auth/google")
    suspend fun loginWithGoogle(@Body request: LoginRequest): ApiResponse<LoginResponse>
    // 토큰 갱신
    @POST("api/v1/auth/refresh")
    suspend fun refreshAccessToken(@Body request: RefreshTokenRequest): ApiResponse<LoginResponse>

    // 로그아웃
    @POST("api/v1/auth/logout")
    suspend fun logout(): Response<Unit>
}