package com.gildongmu.dduru_android.presentation.feature.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.gildongmu.dduru_android.core.common.util.GoogleLoginHelper
import com.gildongmu.dduru_android.core.common.util.KakaoLoginHelper
import com.gildongmu.dduru_android.core.common.util.SocialLoginCallback
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    // 화면 상태 (환영 화면 / 로그인 화면)
    private var currentScreen by mutableStateOf(Screen.WELCOME)

    // 구글 로그인 런처
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            GoogleLoginHelper.handleResult(this, result.data, object : SocialLoginCallback {
                override fun onSuccess(idToken: String) {
                    viewModel.loginToServer(idToken, "GOOGLE")
                }

                override fun onFailure(errorMessage: String?) {
                    showToast("구글 로그인 실패: $errorMessage")
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            DduruTheme {
                val uiState by viewModel.uiState.collectAsState()

                // 로그인 성공 시 메인 화면으로 이동
                LaunchedEffect(uiState) {
                    if (uiState is LoginUiState.Success) {
                        moveToMainActivity()
                    }
                }

                // 에러 발생 시 토스트 표시
                LaunchedEffect(uiState) {
                    if (uiState is LoginUiState.Error) {
                        showToast((uiState as LoginUiState.Error).message)
                    }
                }

                // 화면 전환
                when (currentScreen) {
                    Screen.WELCOME -> {
                        WelcomeScreen(
                            onNextClick = { currentScreen = Screen.LOGIN },
                            onGuestClick = { performGuestLogin() }
                        )
                    }
                    Screen.LOGIN -> {
                        LoginScreen(
                            isLoading = (uiState is LoginUiState.Loading),
                            onKakaoClick = { performKakaoLogin() },
                            onGoogleClick = { performGoogleLogin() },
                            onGuestClick = { performGuestLogin() }
                        )
                    }
                }
            }
        }
    }

    private fun performKakaoLogin() {
        KakaoLoginHelper.login(this, object : SocialLoginCallback {
            override fun onSuccess(idToken: String) {
                viewModel.loginToServer(idToken, "KAKAO")
            }

            override fun onFailure(errorMessage: String?) {
                showToast("카카오 로그인 실패: $errorMessage")
            }
        })
    }

    private fun performGoogleLogin() {
        GoogleLoginHelper.initGoogleLogin(this, googleSignInLauncher)
    }

    private fun performGuestLogin() {
        // TODO: 비회원 상태 저장 (필요시 SharedPreferences 사용)
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    enum class Screen {
        WELCOME, LOGIN
    }
}
