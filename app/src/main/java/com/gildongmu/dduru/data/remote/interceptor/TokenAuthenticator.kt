package com.gildongmu.dduru.data.remote.interceptor

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.dto.request.RefreshTokenRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.LoginResponse
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val baseUrl: String,
    private val json: Json
) : Authenticator {
    private val refreshClient = OkHttpClient()
    private val refreshMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.encodedPath == REFRESH_TOKEN_PATH) return null
        if (response.responseCount >= MAX_AUTH_ATTEMPTS) return null

        return runBlocking {
            val refreshToken = tokenLocalDataSource.refreshToken.first()
                ?.takeIf { it.isNotBlank() }
                ?: return@runBlocking null
            val currentAccessToken = tokenLocalDataSource.accessToken.first()
            val requestAccessToken = response.request.header(AUTHORIZATION_HEADER)
                ?.removePrefix(BEARER_PREFIX)

            if (!currentAccessToken.isNullOrBlank() && currentAccessToken != requestAccessToken) {
                return@runBlocking response.request.withAuthorization(currentAccessToken)
            }

            val refreshedTokens = refreshAndSaveTokens(
                refreshToken = refreshToken,
                staleAccessToken = requestAccessToken
            ) ?: return@runBlocking null

            response.request.withAuthorization(refreshedTokens.accessToken)
        }
    }

    suspend fun refreshAndSaveTokens(
        refreshToken: String,
        staleAccessToken: String? = null
    ): LoginResponse? = refreshMutex.withLock {
        val latestAccessToken = tokenLocalDataSource.accessToken.first()
        val latestRefreshToken = tokenLocalDataSource.refreshToken.first()

        if (
            staleAccessToken != null &&
            !latestAccessToken.isNullOrBlank() &&
            latestAccessToken != staleAccessToken
        ) {
            return@withLock LoginResponse(
                accessToken = latestAccessToken,
                refreshToken = latestRefreshToken.orEmpty()
            )
        }

        val validRefreshToken = refreshToken.takeIf { it.isNotBlank() }
            ?: latestRefreshToken?.takeIf { it.isNotBlank() }
            ?: return@withLock null
        val refreshedTokens = withContext(Dispatchers.IO) {
            refreshTokens(validRefreshToken)
        } ?: return@withLock null
        tokenLocalDataSource.saveTokens(
            accessToken = refreshedTokens.accessToken,
            refreshToken = refreshedTokens.refreshToken
        )
        refreshedTokens
    }

    private fun refreshTokens(refreshToken: String): LoginResponse? {
        val requestBody = json.encodeToString(RefreshTokenRequest(refreshToken))
            .toRequestBody(JSON_MEDIA_TYPE)
        val request = Request.Builder()
            .url(
                baseUrl.toHttpUrl()
                    .newBuilder()
                    .addPathSegments(REFRESH_TOKEN_PATH.removePrefix("/"))
                    .build()
            )
            .post(requestBody)
            .build()

        return runCatching {
            refreshClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null

                val body = response.body?.string() ?: return null
                val apiResponse = json.decodeFromString<ApiResponse<LoginResponse>>(body)
                apiResponse.data
            }
        }.getOrNull()
    }

    private fun Request.withAuthorization(accessToken: String): Request {
        return newBuilder()
            .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX$accessToken")
            .build()
    }

    private val Response.responseCount: Int
        get() {
            var result = 1
            var current = priorResponse
            while (current != null) {
                result++
                current = current.priorResponse
            }
            return result
        }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
        const val REFRESH_TOKEN_PATH = "/api/v1/auth/refresh"
        const val MAX_AUTH_ATTEMPTS = 2
        val JSON_MEDIA_TYPE = "application/json".toMediaType()
    }
}
