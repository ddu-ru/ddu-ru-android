package com.gildongmu.dduru.data.remote.interceptor

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = runBlocking {
            tokenLocalDataSource.accessToken.first()
        }

        val requestBuilder = original.newBuilder()
            .addHeader("Content-Type", "application/json")

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}
