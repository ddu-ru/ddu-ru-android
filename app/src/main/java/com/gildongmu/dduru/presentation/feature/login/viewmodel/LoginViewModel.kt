package com.gildongmu.dduru.presentation.feature.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.remote.datasource.KakaoAuthDataSource
import com.gildongmu.dduru.domain.usecase.LoginGoogleUseCase
import com.gildongmu.dduru.domain.usecase.LoginKakaoUseCase
import com.gildongmu.dduru.presentation.feature.login.util.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginGoogleUseCase: LoginGoogleUseCase,
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val kakaoAuthDataSource: KakaoAuthDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun startGoogleLogin(context: Context) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = loginGoogleUseCase(context)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Google Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("구글 로그인 실패")
            }
        }
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
