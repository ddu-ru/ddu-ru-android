package com.gildongmu.dduru_android.presentation.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.domain.repository.AuthRepository
import com.gildongmu.dduru_android.domain.usecase.LoginGoogleUseCase
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
    data class Success(val isNewUser: Boolean) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginGoogleUseCase: LoginGoogleUseCase,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    // 서버에 로그인 요청
    fun loginToServer(idToken: String, provider: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            // 사용자가 로그인을 중간에 취소한 경우 (Google 등)
            if (idToken == "CANCELED") {
                _uiState.value = LoginUiState.Idle
                return@launch
            }

            val result: Result<Boolean> = when (provider) {
                "KAKAO" -> authRepository.loginWithKakao(idToken)
                "GOOGLE" -> loginGoogleUseCase(idToken)
                else -> throw IllegalArgumentException("Unknown provider: $provider")
            }

            result.onSuccess { isNewUser ->
                // 성공 시
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("로그인에 실패했습니다. 네트워크를 확인해주세요.")
            }
        }
    }
}