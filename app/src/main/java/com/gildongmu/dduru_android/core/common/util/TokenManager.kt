package com.gildongmu.dduru_android.core.common.util

import android.content.Context
import android.util.Log
import com.gildongmu.dduru_android.data.datastore.TokenDataStore
import com.gildongmu.dduru_android.data.model.request.RefreshTokenRequest
import com.gildongmu.dduru_android.data.service.AuthService
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

            val response = authService.refreshAccessToken(request)

            tokenDataStore.saveTokens(response.accessToken, response.refreshToken)
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


