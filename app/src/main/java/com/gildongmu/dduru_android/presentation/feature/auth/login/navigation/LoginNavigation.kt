package com.gildongmu.dduru_android.presentation.feature.auth.login.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru_android.core.common.util.GoogleLoginHelper
import com.gildongmu.dduru_android.core.common.util.KakaoLoginHelper
import com.gildongmu.dduru_android.core.common.util.SocialLoginCallback
import com.gildongmu.dduru_android.presentation.feature.auth.login.ui.LoginScreen
import com.gildongmu.dduru_android.presentation.feature.auth.login.viewmodel.LoginUiState
import com.gildongmu.dduru_android.presentation.feature.auth.login.viewmodel.LoginViewModel

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onGuestClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // 1. 구글 로그인 런처 설정
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val activity = context.findActivity()
            if (activity != null) {
                // context 대신 activity를 전달
                GoogleLoginHelper.handleResult(activity, result.data, object : SocialLoginCallback {
                    override fun onSuccess(idToken: String) {
                        viewModel.loginToServer(idToken, "GOOGLE")
                    }
                    override fun onFailure(errorMessage: String?) {
                        Toast.makeText(context, "구글 로그인 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(context, "Activity를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 2. 상태 관찰
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
        if (uiState is LoginUiState.Error) {
            val msg = (uiState as LoginUiState.Error).message
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    // 3. UI
    LoginScreen(
        isLoading = (uiState is LoginUiState.Loading),
        onKakaoClick = {
            KakaoLoginHelper.login(context, object : SocialLoginCallback {
                override fun onSuccess(idToken: String) {
                    viewModel.loginToServer(idToken, "KAKAO")
                }
                override fun onFailure(errorMessage: String?) {
                    Toast.makeText(context, "카카오 로그인 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            })
        },
        onGoogleClick = {
            val activity = context.findActivity()
            if (activity != null) {
                GoogleLoginHelper.initGoogleLogin(activity, googleSignInLauncher)
            } else {
                Toast.makeText(context, "Activity를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        },
        onGuestClick = onGuestClick
    )
}

//  Context -> Activity 변환
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
