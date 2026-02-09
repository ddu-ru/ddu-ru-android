package com.gildongmu.dduru.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.BuildConfig
import com.gildongmu.dduru.presentation.feature.login.LoginScreen
import com.gildongmu.dduru.presentation.feature.login.LoginUiState
import com.gildongmu.dduru.presentation.feature.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    onGuestClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val googleSignInClient = remember(context) {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()
        GoogleSignIn.getClient(context, options)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (!idToken.isNullOrBlank()) {
                    viewModel.loginWithGoogle(idToken)
                } else {
                    viewModel.onGoogleLoginError("구글 로그인 토큰이 비어 있습니다.")
                }
            } catch (e: ApiException) {
                viewModel.onGoogleLoginError("구글 로그인 실패: ${e.statusCode}")
            }
        } else {
            viewModel.onGoogleLoginError("구글 로그인을 취소했어요.")
        }
    }

    //  상태 관찰
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

    // UI 표시
    LoginScreen(
        isLoading = (uiState is LoginUiState.Loading),
        onKakaoClick = { viewModel.startKakaoLogin() },
        onGoogleClick = {
            launcher.launch(googleSignInClient.signInIntent)
        },
        onGuestClick = onGuestClick
    )
}
