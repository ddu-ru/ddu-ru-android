package com.gildongmu.dduru.presentation.feature.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginBinder(
    onLoginSuccess: (Boolean) -> Unit,
    onGuestClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> {
                onLoginSuccess(state.isNewUser)
            }
            is LoginUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LoginScreen(
        isLoading = (uiState is LoginUiState.Loading),
        onKakaoClick = { viewModel.startKakaoLogin() },
        onGoogleClick = {
            viewModel.startGoogleLogin(context)
        },
        onGuestClick = onGuestClick
    )
}
