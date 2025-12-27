package com.gildongmu.dduru_android.data.remote.datasource

interface KakaoAuthDataSource {
    suspend fun getIdToken(): String?
}