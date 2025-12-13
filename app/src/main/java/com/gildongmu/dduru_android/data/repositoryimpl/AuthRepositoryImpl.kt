package com.gildongmu.dduru_android.data.repositoryimpl

import com.gildongmu.dduru_android.data.datastore.TokenDataStore
import com.gildongmu.dduru_android.data.remote.datasource.AuthDataSource
import com.gildongmu.dduru_android.data.remote.dto.request.LoginRequest
import com.gildongmu.dduru_android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithKakao(LoginRequest(idToken))

            if (response.data != null) {
                tokenDataStore.saveTokens(response.data.accessToken, response.data.refreshToken)

                Result.success(response.data.isNewUser)
            } else {
                Result.failure(Exception("Data is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<Boolean> {
        return try {
            val response = authDataSource.loginWithGoogle(LoginRequest(idToken))

            if (response.data != null) {
                tokenDataStore.saveTokens(response.data.accessToken, response.data.refreshToken)

                Result.success(response.data.isNewUser)
            } else {
                Result.failure(Exception("Data is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
