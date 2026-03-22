package com.gildongmu.dduru.presentation.feature.login.util

sealed interface LoginUiEvent {
    data class LoginSuccess(val isNewUser: Boolean) : LoginUiEvent
    data class ShowError(val message: String) : LoginUiEvent
}
