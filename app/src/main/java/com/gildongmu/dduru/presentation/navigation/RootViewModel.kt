package com.gildongmu.dduru.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class RootViewModel @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
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

                if (accessToken.isNullOrBlank()) {
                    RootNavRoute.LoginNavRoute
                } else {
                    RootNavRoute.MainNavRoute
                }

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                Log.e("RootViewModel", "Failed to resolve start destination", e)

                RootNavRoute.LoginNavRoute
            }
        }
    }
}
