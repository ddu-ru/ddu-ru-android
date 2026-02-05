package com.gildongmu.dduru.data.local

import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}
