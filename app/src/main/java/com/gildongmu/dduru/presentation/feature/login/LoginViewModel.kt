package com.gildongmu.dduru.presentation.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.datasource.KakaoAuthDataSource
import com.gildongmu.dduru.domain.repository.AuthRepository
import com.gildongmu.dduru.domain.usecase.LoginKakaoUseCase
import com.gildongmu.dduru.presentation.feature.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// UI 상태 정의
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val isNewUser: Boolean) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val kakaoAuthDataSource: KakaoAuthDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    // 앱 시작시 이동할 화면 상태 관리
    private val _startScreen = MutableStateFlow<Screen>(Screen.Splash)
    val startScreen: StateFlow<Screen> = _startScreen

    init {
        checkLoginStatus()
    }
    private fun checkLoginStatus() {
        viewModelScope.launch {
            val accessToken = tokenLocalDataSource.accessToken.first()
            if (!accessToken.isNullOrBlank()) {
                _startScreen.value = Screen.Home
            } else {
                _startScreen.value = Screen.Login
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = authRepository.loginWithGoogle(idToken)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Google Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("구글 로그인 실패")
            }
        }
    }

    fun onGoogleLoginError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun startKakaoLogin() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val idToken = kakaoAuthDataSource.getIdToken()
            if (idToken.isNullOrBlank()) {
                _uiState.value = LoginUiState.Idle
                return@launch
            }

            val result = loginKakaoUseCase(idToken)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Kakao Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("카카오 로그인 실패: ${e.message}")
            }
        }
    }

    fun loginWithKakao(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = loginKakaoUseCase(idToken)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Kakao Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("카카오 로그인 실패")
            }
        }
    }
}
