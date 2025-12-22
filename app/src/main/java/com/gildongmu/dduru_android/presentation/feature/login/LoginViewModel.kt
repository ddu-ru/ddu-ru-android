package com.gildongmu.dduru_android.presentation.feature.login

import android.content.Context
import android.content.Intent
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

    fun getGoogleIntent(): Intent {
        return authRepository.getGoogleSignInIntent()
    }
    fun handleGoogleResult(data: Intent?) {
        viewModelScope.launch {
            if (data == null) {
                // 데이터가 없으면 취소나 실패로 간주
                return@launch
            }

            _uiState.value = LoginUiState.Loading

            // UseCase에게 Intent를 통째로 넘김 (파싱은 Data Layer가 알아서 함)
            val result = loginGoogleUseCase(data)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Google Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("구글 로그인 실패: ${e.message}")
            }
        }
    }
    fun loginWithKakao(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = authRepository.loginWithKakao(idToken)

            result.onSuccess { isNewUser ->
                _uiState.value = LoginUiState.Success(isNewUser)
            }.onFailure { e ->
                Log.e("LoginViewModel", "Kakao Login failed: ${e.message}", e)
                _uiState.value = LoginUiState.Error("카카오 로그인 실패")
            }
        }
    }
}
