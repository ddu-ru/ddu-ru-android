package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithKakao(LoginRequest(idToken))

            val data = response.data ?: return Result.failure(
                Exception("Login response data is null")
            )

            tokenLocalDataSource.saveTokens(data.accessToken, data.refreshToken)
            Result.success(data.isNewUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithGoogle(LoginRequest(idToken))

            val data = response.data ?: return Result.failure(Exception("Data is null"))

            tokenLocalDataSource.saveTokens(
                data.accessToken,
                data.refreshToken
            )
            Result.success(data.isNewUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
