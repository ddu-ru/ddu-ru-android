package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.LoginResponse

interface AuthDataSource {
    // 카카오 로그인 요청 함수 정의
    suspend fun loginWithKakao(request: LoginRequest): ApiResponse<LoginResponse>

    // 구글 로그인 요청 함수 정의
    suspend fun loginWithGoogle(request: LoginRequest): ApiResponse<LoginResponse>
}
