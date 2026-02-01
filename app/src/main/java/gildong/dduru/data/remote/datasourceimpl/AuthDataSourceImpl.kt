package gildong.dduru.data.remote.datasourceimpl

import gildong.dduru.data.remote.datasource.AuthDataSource
import gildong.dduru.data.remote.dto.request.LoginRequest
import gildong.dduru.data.remote.dto.response.ApiResponse
import gildong.dduru.data.remote.dto.response.LoginResponse
import gildong.dduru.data.remote.service.AuthService
import javax.inject.Inject

// @Inject를 통해 AuthService를 주입
class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {

    override suspend fun loginWithKakao(request: LoginRequest): ApiResponse<LoginResponse> {
        // 실제 Retrofit 서비스 호출
        return authService.loginWithKakao(request)
    }

    override suspend fun loginWithGoogle(request: LoginRequest): ApiResponse<LoginResponse> {
        // 실제 Retrofit 서비스 호출
        return authService.loginWithGoogle(request)
    }
}
