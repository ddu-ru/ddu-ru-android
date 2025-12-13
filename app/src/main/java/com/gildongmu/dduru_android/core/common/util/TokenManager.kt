package com.gildongmu.dduru_android.core.common.util

import android.content.Context
import android.util.Log
import com.gildongmu.dduru_android.data.datastore.TokenDataStore
import com.gildongmu.dduru_android.data.remote.dto.request.RefreshTokenRequest
import com.gildongmu.dduru_android.data.remote.service.AuthService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService
) {

    private val tokenDataStore = TokenDataStore(context)

    suspend fun refreshToken(): Boolean {
        return try {
            val refreshToken = tokenDataStore.getRefreshToken() ?: return false
            val request = RefreshTokenRequest(refreshToken)

            // 1. 서버 응답은 ApiResponse<LoginResponse> 형태입니다.
            val apiResponse = authService.refreshAccessToken(request)

            // 2. 응답 상태 체크 (API status가 0이어야 성공)
//            if (apiResponse.status != 0) {
//                // HTTP 200이라도 status가 0이 아니면 논리적 오류입니다.
//                //val errorMsg = apiResponse.message ?: "API Status Error: ${apiResponse.status}"
//                //Log.e("TokenManager", "토큰 갱신 실패: $errorMsg")
//                return false
//            }

            // 3. 실제 데이터(data 필드)를 꺼냅니다.
            val responseData = apiResponse.data ?: run {
                Log.e("TokenManager", "토큰 갱신 실패: 데이터 필드가 null입니다.")
                return false
            }

            // 4. data 필드 안의 accessToken과 refreshToken을 저장합니다.
            tokenDataStore.saveTokens(responseData.accessToken, responseData.refreshToken)
            true
        } catch (e: Exception) {
            Log.e("TokenManager", "토큰 갱신 실패", e)
            false
        }
    }

    suspend fun logout(): Boolean {
        return try {
            // Logout은 Response<Unit>으로 받기 때문에 DTO 구조를 확인할 필요가 없습니다.
            val response = authService.logout()
            if (response.isSuccessful) {
                tokenDataStore.clearTokens()
                true
            } else {
                Log.e("TokenManager", "로그아웃 실패 ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("TokenManager", "로그아웃 실패 ${e.message}")
            false
        }
    }
}
