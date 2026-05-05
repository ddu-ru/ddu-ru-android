package com.gildongmu.dduru.presentation.navigation

import androidx.compose.runtime.Composable
import com.gildongmu.dduru.presentation.feature.login.screen.LoginRoute as LoginScreenRoute

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    onGuestClick: () -> Unit
) {
    LoginScreenRoute(
        onLoginSuccess = onLoginSuccess,
        onGuestClick = onGuestClick
    )
}
