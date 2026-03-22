package com.gildongmu.dduru.presentation.feature.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.data.remote.datasource.KakaoAuthDataSource
import com.gildongmu.dduru.domain.usecase.LoginGoogleUseCase
import com.gildongmu.dduru.domain.usecase.LoginKakaoUseCase
import com.gildongmu.dduru.presentation.feature.login.util.LoginUiEvent
import com.gildongmu.dduru.presentation.feature.login.util.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginGoogleUseCase: LoginGoogleUseCase,
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val kakaoAuthDataSource: KakaoAuthDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> = _uiEvent.asSharedFlow()

    fun startGoogleLogin(context: Context) {
        viewModelScope.launch {
            updateLoading(true)

            val result = loginGoogleUseCase(context)

            result.onSuccess { isNewUser ->
                updateLoading(false)
                _uiEvent.emit(LoginUiEvent.LoginSuccess(isNewUser))
            }.onFailure { e ->
                Log.e("LoginViewModel", "Google Login failed: ${e.message}", e)
                updateLoading(false)
                _uiEvent.emit(LoginUiEvent.ShowError("구글 로그인 실패"))
            }
        }
    }

    fun startKakaoLogin() {
        viewModelScope.launch {
            updateLoading(true)

            val idToken = kakaoAuthDataSource.getIdToken()
            if (idToken.isNullOrBlank()) {
                updateLoading(false)
                _uiEvent.emit(LoginUiEvent.ShowError("카카오 로그인에 실패했습니다. 다시 시도해주세요."))
                return@launch
            }

            loginKakao(idToken)
        }
    }

    fun loginWithKakao(idToken: String) {
        viewModelScope.launch {
            updateLoading(true)
            loginKakao(idToken)
        }
    }

    private suspend fun loginKakao(idToken: String) {
        val result = loginKakaoUseCase(idToken)

        result.onSuccess { isNewUser ->
            updateLoading(false)
            _uiEvent.emit(LoginUiEvent.LoginSuccess(isNewUser))
        }.onFailure { e ->
            Log.e("LoginViewModel", "Kakao Login failed: ${e.message}", e)
            updateLoading(false)
            _uiEvent.emit(LoginUiEvent.ShowError("카카오 로그인 실패"))
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}
