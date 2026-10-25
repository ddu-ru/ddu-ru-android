package com.gildongmu.dduru.domain.repository

interface FcmTokenRepository {
    suspend fun registerToken(token: String): Result<Unit>

    suspend fun registerCurrentToken(): Result<Unit>

    suspend fun deleteToken(token: String): Result<Unit>

    suspend fun deleteCurrentToken(): Result<Unit>
}
