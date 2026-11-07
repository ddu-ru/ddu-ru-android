package com.gildongmu.dduru.data.auth

import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.TokenAuthenticator
import com.gildongmu.dduru.domain.auth.LoginSessionChecker
import com.gildongmu.dduru.domain.auth.LoginSessionState
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class LoginSessionCheckerImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator
) : LoginSessionChecker {

    override suspend fun check(): LoginSessionState {
        val accessToken = tokenLocalDataSource.accessToken.first()
        if (accessToken.isNullOrBlank()) return LoginSessionState.EXPIRED
        if (!tokenAuthenticator.shouldRefresh(accessToken)) return LoginSessionState.VALID

        val refreshToken = tokenLocalDataSource.refreshToken.first()
        if (refreshToken.isNullOrBlank()) return LoginSessionState.EXPIRED

        val refreshedTokens = tokenAuthenticator.refreshAndSaveTokens(
            refreshToken = refreshToken,
            staleAccessToken = accessToken
        )
        return when {
            refreshedTokens != null -> LoginSessionState.VALID
            tokenLocalDataSource.accessToken.first().isNullOrBlank() -> LoginSessionState.EXPIRED
            else -> LoginSessionState.UNKNOWN
        }
    }
}
