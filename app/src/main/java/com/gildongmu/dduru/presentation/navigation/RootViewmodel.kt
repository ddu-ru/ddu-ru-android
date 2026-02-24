package com.gildongmu.dduru.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            val accessToken = tokenLocalDataSource.accessToken.first()
            if (accessToken.isNullOrBlank()) {
                _startDestination.value = RootNavRoute.LoginNavRoute
            } else {
                _startDestination.value = RootNavRoute.MainNavRoute
            }
        }
    }
}
