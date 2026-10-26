package com.gildongmu.dduru.data.remote.datasource

import retrofit2.Response

interface FcmTokenRemoteDataSource {
    suspend fun registerToken(token: String): Response<Unit>

    suspend fun deleteToken(token: String): Response<Unit>
}
