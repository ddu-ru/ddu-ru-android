package com.gildongmu.dduru.presentation.feature.login.util

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val isNewUser: Boolean) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
