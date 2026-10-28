package com.gildongmu.dduru.data.remote.interceptor

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // 갱신 요청 자체에는 만료된 access token을 붙이거나 재귀적으로 갱신을 시도하지 않는다.
        if (original.url.encodedPath == REFRESH_TOKEN_PATH) {
            return chain.proceed(original)
        }

        val token = runBlocking {
            val accessToken = tokenLocalDataSource.accessToken.first()

            if (!accessToken.isNullOrBlank() && tokenAuthenticator.shouldRefresh(accessToken)) {
                val refreshToken = tokenLocalDataSource.refreshToken.first()
                if (refreshToken.isNullOrBlank()) {
                    accessToken
                } else {
                    tokenAuthenticator.refreshAndSaveTokens(
                        refreshToken = refreshToken,
                        staleAccessToken = accessToken
                    )?.accessToken ?: accessToken
                }
            } else {
                accessToken
            }
        }

        val requestBuilder = original.newBuilder()

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(requestBuilder.build())
    }

    private companion object {
        const val REFRESH_TOKEN_PATH = "/api/v1/auth/refresh"
    }
}
