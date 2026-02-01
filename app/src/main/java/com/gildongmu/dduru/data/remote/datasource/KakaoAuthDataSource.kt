package com.gildongmu.dduru.data.remote.datasource

interface KakaoAuthDataSource {
    suspend fun getIdToken(): String?
}
