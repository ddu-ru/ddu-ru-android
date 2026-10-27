package com.gildongmu.dduru.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.TokenAuthenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class RootViewModel @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator
) : ViewModel() {

    private val _startDestination = MutableStateFlow<RootNavRoute?>(null)
    val startDestination: StateFlow<RootNavRoute?> = _startDestination.asStateFlow()
    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _startDestination.value = try {
                val accessToken = tokenLocalDataSource.accessToken.first()

                when {
                    accessToken.isNullOrBlank() -> RootNavRoute.Login
                    !tokenAuthenticator.shouldRefresh(accessToken) -> RootNavRoute.Main
                    else -> {
                        val refreshToken = tokenLocalDataSource.refreshToken.first()
                        val refreshedTokens = refreshToken
                            ?.takeIf { it.isNotBlank() }
                            ?.let {
                                tokenAuthenticator.refreshAndSaveTokens(
                                    refreshToken = it,
                                    staleAccessToken = accessToken
                                )
                            }

                        if (refreshedTokens == null) RootNavRoute.Login else RootNavRoute.Main
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("RootViewModel", "Failed to resolve start destination", e)

                RootNavRoute.Login
            }
        }
    }
}
