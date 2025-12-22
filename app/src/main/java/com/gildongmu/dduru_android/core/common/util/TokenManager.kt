package com.gildongmu.dduru_android.core.common.util

import android.util.Log
import com.gildongmu.dduru_android.data.local.TokenLocalDataSource
import com.gildongmu.dduru_android.data.remote.dto.request.RefreshTokenRequest
import com.gildongmu.dduru_android.data.remote.service.AuthService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val authService: AuthService,
    private val tokenLocalDataSource: TokenLocalDataSource
) {

    suspend fun refreshToken(): Boolean {
        return try {
            val refreshToken = tokenLocalDataSource.refreshToken.first() ?: return false
            val request = RefreshTokenRequest(refreshToken)

            val apiResponse = authService.refreshAccessToken(request)

            val responseData = apiResponse.data ?: run {
                Log.e("TokenManager", "토큰 갱신 실패: 데이터 필드가 null입니다.")
                return false
            }

            tokenLocalDataSource.saveTokens(responseData.accessToken, responseData.refreshToken)
            true
        } catch (e: Exception) {
            Log.e("TokenManager", "토큰 갱신 실패", e)
            false
        }
    }

    suspend fun logout(): Boolean {
        return try {
            val response = authService.logout()
            if (response.isSuccessful) {
                tokenLocalDataSource.clearTokens()
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
