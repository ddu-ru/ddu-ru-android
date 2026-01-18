package com.gildongmu.dduru_android.data.remote.interceptor

import com.gildongmu.dduru_android.data.local.TokenLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            tokenLocalDataSource.accessToken.first()
        }

        val originalRequest = chain.request()

        // 2. 토큰이 없으면 그냥
        if (accessToken.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        // 3. 토큰이 있으면 헤더에 붙여서
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}
