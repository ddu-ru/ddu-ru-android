package com.gildongmu.dduru.data.remote.interceptor

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenLocalDataSource.accessToken.first() }

        if (accessToken.isNullOrBlank()) {
            return chain.proceed(chain.request())
        }

        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(request)
    }
}
