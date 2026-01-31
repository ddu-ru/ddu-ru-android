package com.gildongmu.dduru_android.data.repositoryimpl

import android.content.Context
import com.gildongmu.dduru_android.data.local.TokenLocalDataSource
import com.gildongmu.dduru_android.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru_android.data.remote.datasource.GoogleAuthDataSource
import com.gildongmu.dduru_android.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru_android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithKakao(LoginRequest(idToken))

            val data = response.data ?: return Result.failure(Exception("Login response data is null"))

            tokenLocalDataSource.saveTokens(data.accessToken, data.refreshToken)
            Result.success(data.isNewUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(context: Context): Result<Boolean> {
        return try {
            val idToken = googleAuthDataSource.getGoogleIdToken(context)
                ?: return Result.failure(Exception("Google ID Token이 없습니다."))

            val response = authDataSource.loginWithGoogle(LoginRequest(idToken))

            if (response.data != null) {
                tokenLocalDataSource.saveTokens(response.data.accessToken, response.data.refreshToken)
                Result.success(response.data.isNewUser)
            } else {
                Result.failure(Exception("Data is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
