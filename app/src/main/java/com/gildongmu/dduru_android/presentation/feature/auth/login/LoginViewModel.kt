package com.gildongmu.dduru_android.presentation.feature.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.data.datastore.TokenDataStore
import com.gildongmu.dduru_android.data.model.request.LoginRequest
import com.gildongmu.dduru_android.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI 상태 정의
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val tokenDataStore = TokenDataStore(context)

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    /**
     * 서버에 로그인 요청
     * @param idToken 소셜 로그인에서 받은 ID 토큰
     * @param provider "KAKAO" 또는 "GOOGLE"
     */
    fun loginToServer(idToken: String, provider: String) {
        viewModelScope.launch {
            try {
                _uiState.value = LoginUiState.Loading

                // 서버에 로그인 요청
                val response = when (provider) {
                    "KAKAO" -> authService.loginWithKakao(LoginRequest(idToken))
                    "GOOGLE" -> authService.loginWithGoogle(LoginRequest(idToken))
                    else -> throw IllegalArgumentException("Unknown provider: $provider")
                }

                // 토큰 저장
                tokenDataStore.saveTokens(response.accessToken, response.refreshToken)

                _uiState.value = LoginUiState.Success
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("로그인 실패: ${e.message}")
            }
        }
    }
}
