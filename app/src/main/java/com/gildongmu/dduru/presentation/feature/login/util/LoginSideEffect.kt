package com.gildongmu.dduru.presentation.feature.login.util

sealed interface LoginSideEffect {
    data class LoginSuccess(val isNewUser: Boolean) : LoginSideEffect
    data class ShowError(val message: String) : LoginSideEffect
}
