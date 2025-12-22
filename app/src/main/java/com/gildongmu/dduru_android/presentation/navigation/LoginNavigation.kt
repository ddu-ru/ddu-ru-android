package com.gildongmu.dduru_android.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru_android.core.common.util.KakaoLoginHelper
import com.gildongmu.dduru_android.core.common.util.SocialLoginCallback
import com.gildongmu.dduru_android.presentation.feature.login.LoginScreen
import com.gildongmu.dduru_android.presentation.feature.login.LoginUiState
import com.gildongmu.dduru_android.presentation.feature.login.LoginViewModel

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    onGuestClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // 1. 구글 로그인 런처
    val googleLoginLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleGoogleResult(result.data)
    }

    // 2. 상태 관찰
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

    // 3. UI 표시
    LoginScreen(
        isLoading = (uiState is LoginUiState.Loading),
        onKakaoClick = {
            KakaoLoginHelper.login(context, object : SocialLoginCallback {
                override fun onSuccess(idToken: String) {
                    viewModel.loginWithKakao(idToken)
                }
                override fun onFailure(errorMessage: String?) {
                    Toast.makeText(context, "카카오 로그인 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            })
        },
        onGoogleClick = {
            val intent = viewModel.getGoogleIntent()
            googleLoginLauncher.launch(intent)
        },
        onGuestClick = onGuestClick
    )
}
