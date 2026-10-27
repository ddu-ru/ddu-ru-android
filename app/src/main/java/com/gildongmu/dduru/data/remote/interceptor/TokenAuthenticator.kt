package com.gildongmu.dduru.data.remote.interceptor

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.dto.request.RefreshTokenRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.LoginResponse
import java.util.Base64
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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
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
        when (val result = withContext(Dispatchers.IO) { refreshTokens(validRefreshToken) }) {
            is RefreshResult.Success -> {
                tokenLocalDataSource.saveTokens(
                    accessToken = result.tokens.accessToken,
                    refreshToken = result.tokens.refreshToken
                )
                result.tokens
            }

            RefreshResult.InvalidToken -> {
                tokenLocalDataSource.clearTokens()
                null
            }

            RefreshResult.Failure -> null
        }
    }

    fun shouldRefresh(accessToken: String): Boolean {
        return runCatching {
            val payload = accessToken.split(".").getOrNull(JWT_PAYLOAD_INDEX) ?: return true
            val decodedPayload = String(Base64.getUrlDecoder().decode(payload), Charsets.UTF_8)
            val expiresAt = json.parseToJsonElement(decodedPayload)
                .jsonObject[JWT_EXPIRES_AT_KEY]
                ?.jsonPrimitive
                ?.longOrNull
                ?: return true
            val now = System.currentTimeMillis() / MILLIS_PER_SECOND

            expiresAt <= now + TOKEN_REFRESH_BUFFER_SECONDS
        }.getOrDefault(true)
    }

    private fun refreshTokens(refreshToken: String): RefreshResult {
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
                if (response.code in INVALID_TOKEN_STATUS_RANGE) {
                    return RefreshResult.InvalidToken
                }
                if (!response.isSuccessful) return RefreshResult.Failure

                val body = response.body?.string() ?: return RefreshResult.Failure
                val apiResponse = json.decodeFromString<ApiResponse<LoginResponse>>(body)
                apiResponse.data?.takeIf { responseData ->
                    apiResponse.status in HTTP_SUCCESS_STATUS_RANGE &&
                        responseData.accessToken.isNotBlank() &&
                        responseData.refreshToken.isNotBlank()
                }?.let(RefreshResult::Success) ?: RefreshResult.Failure
            }
        }.getOrDefault(RefreshResult.Failure)
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
        val HTTP_SUCCESS_STATUS_RANGE = 200..299
        val INVALID_TOKEN_STATUS_RANGE = 400..401
        const val JWT_PAYLOAD_INDEX = 1
        const val JWT_EXPIRES_AT_KEY = "exp"
        const val MILLIS_PER_SECOND = 1_000L
        const val TOKEN_REFRESH_BUFFER_SECONDS = 60L
        val JSON_MEDIA_TYPE = "application/json".toMediaType()
    }
}

private sealed interface RefreshResult {
    data class Success(val tokens: LoginResponse) : RefreshResult
    data object InvalidToken : RefreshResult
    data object Failure : RefreshResult
}
