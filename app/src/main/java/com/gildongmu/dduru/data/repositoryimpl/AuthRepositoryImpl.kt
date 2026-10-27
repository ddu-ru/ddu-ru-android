package com.gildongmu.dduru.data.repositoryimpl

import android.content.Context
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru.data.remote.datasource.GoogleAuthDataSource
import com.gildongmu.dduru.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru.domain.repository.AuthRepository
import com.gildongmu.dduru.domain.repository.FcmTokenRepository
import javax.inject.Inject
import timber.log.Timber

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    private val fcmTokenRepository: FcmTokenRepository
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithKakao(LoginRequest(idToken))

            val data = response.data?.takeIf {
                response.status in HTTP_SUCCESS_STATUS_RANGE &&
                    it.accessToken.isNotBlank() &&
                    it.refreshToken.isNotBlank()
            } ?: return Result.failure(
                Exception("Login response data is null")
            )

            tokenLocalDataSource.saveTokens(data.accessToken, data.refreshToken)
            registerFcmToken()
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

            val data = response.data?.takeIf {
                response.status in HTTP_SUCCESS_STATUS_RANGE &&
                    it.accessToken.isNotBlank() &&
                    it.refreshToken.isNotBlank()
            }

            if (data != null) {
                tokenLocalDataSource.saveTokens(
                    data.accessToken,
                    data.refreshToken
                )
                registerFcmToken()
                Result.success(data.isNewUser)
            } else {
                Result.failure(Exception("Data is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun registerFcmToken() {
        fcmTokenRepository.registerCurrentToken()
            .onFailure { throwable ->
                Timber.e(throwable, "Failed to register FCM token")
            }
    }

    private companion object {
        val HTTP_SUCCESS_STATUS_RANGE = 200..299
    }
}
