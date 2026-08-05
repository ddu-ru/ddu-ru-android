package com.gildongmu.dduru.data.remote.interceptor

import android.util.Base64
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator,
    private val json: Json
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = runBlocking {
            val accessToken = tokenLocalDataSource.accessToken.first()

            if (!accessToken.isNullOrBlank() && accessToken.isExpiredJwt()) {
                val refreshToken = tokenLocalDataSource.refreshToken.first()
                if (refreshToken.isNullOrBlank()) {
                    accessToken
                } else {
                    tokenAuthenticator.refreshAndSaveTokens(refreshToken)?.accessToken ?: accessToken
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

    private fun String.isExpiredJwt(): Boolean {
        return runCatching {
            val payload = split(".").getOrNull(JWT_PAYLOAD_INDEX) ?: return false
            val decodedPayload = String(
                Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP),
                Charsets.UTF_8
            )
            val expiresAt = json.parseToJsonElement(decodedPayload)
                .jsonObject[JWT_EXPIRES_AT_KEY]
                ?.jsonPrimitive
                ?.longOrNull
                ?: return false
            val now = System.currentTimeMillis() / MILLIS_PER_SECOND

            expiresAt <= now + TOKEN_REFRESH_BUFFER_SECONDS
        }.getOrDefault(false)
    }

    private companion object {
        const val JWT_PAYLOAD_INDEX = 1
        const val JWT_EXPIRES_AT_KEY = "exp"
        const val MILLIS_PER_SECOND = 1_000L
        const val TOKEN_REFRESH_BUFFER_SECONDS = 60L
    }
}
