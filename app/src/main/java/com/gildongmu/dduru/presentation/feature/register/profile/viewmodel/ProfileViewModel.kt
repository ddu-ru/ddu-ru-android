package com.gildongmu.dduru.presentation.feature.register.profile.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidationResult
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidator
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileUiState
import com.gildongmu.dduru.presentation.feature.register.profile.util.SurveyStatus
import com.gildongmu.dduru.presentation.feature.register.profile.util.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nicknameRepository: NicknameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            selectedBgColor = ProfileBackgroundColorList.first().color,
            savedBgColor = ProfileBackgroundColorList.first().color
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _characterImageRes = MutableStateFlow(R.drawable.img_penguin_personallity)
    val characterImageRes = _characterImageRes.asStateFlow()

    val radioOptions = listOf(
        ProfileImageCropOption(characterImageRes.value, ProfileEditOption.CHARACTER, 1.6f, 0.16f),
        ProfileImageCropOption(R.drawable.img_blank_profile, ProfileEditOption.CUSTOM)
    )

    val bgColors = ProfileBackgroundColorList
    val maxIntroLength = 60
    private var nicknameCheckJob: Job? = null

    init {
        _uiState.update {
            it.copy(
                isSurveyComplete = SurveyStatus.SKIPPED,
                selectedOption = radioOptions.last().type
            )
        }
    }

    fun onNicknameChanged(newNickname: String) {
        val validationResult = NicknameValidator.validate(newNickname)
        val isValid = validationResult == NicknameValidationResult.VALID
        nicknameCheckJob?.cancel()

        _uiState.update { currentState ->
            currentState.copy(
                nickname = newNickname,
                nicknameErrorMessage = when {
                    newNickname.isEmpty() -> null
                    isValid -> null
                    else -> validationResult.toMessage()
                },
                isNicknameValid = isValid,
                isDuplicationChecked = false,
                isDuplicationChecking = false
            )
        }

        if (isValid) {
            scheduleDuplicationCheck(newNickname)
        }

        checkCanStart()
    }

    fun onRandomNickname() {
        viewModelScope.launch {
            nicknameRepository.getRandomNickname()
                .onSuccess { randomNickname ->
                    onNicknameChanged(randomNickname)
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            nicknameErrorMessage = e.message ?: "랜덤 닉네임을 받아오지 못했어요."
                        )
                    }
                    checkCanStart()
                }
        }
    }

    fun onProfileOptionSelected(option: ProfileImageCropOption) {
        if (
            option.type == ProfileEditOption.CHARACTER &&
            _uiState.value.isSurveyComplete == SurveyStatus.SKIPPED
        ) {
            return
        }
        _uiState.update { it.copy(selectedOption = option.type) }
    }

    fun onIntroductionChanged(newText: String) {
        if (newText.length <= maxIntroLength) {
            _uiState.update { it.copy(introduction = newText) }
        }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri) }
    }

    fun onBgColorSelected(color: Color) {
        _uiState.update { it.copy(selectedBgColor = color) }
    }

    fun commitBgColorSelection() {
        _uiState.update { it.copy(savedBgColor = it.selectedBgColor) }
    }

    private fun scheduleDuplicationCheck(nickname: String) {
        nicknameCheckJob = viewModelScope.launch {
            delay(1500)
            checkNicknameDuplication(nickname)
        }
    }

    private suspend fun checkNicknameDuplication(nickname: String) {
        if (_uiState.value.nickname != nickname) return
        if (NicknameValidator.validate(nickname) != NicknameValidationResult.VALID) return

        _uiState.update { it.copy(isDuplicationChecking = true, isDuplicationChecked = false) }

        val result = nicknameRepository.sanitizeNickname(nickname)
        if (_uiState.value.nickname != nickname) return

        result.onSuccess {
            _uiState.update { state ->
                state.copy(
                    isDuplicationChecking = false,
                    isDuplicationChecked = it,
                    nicknameErrorMessage = if (it) null else "이미 사용 중인 닉네임이에요."
                )
            }
        }.onFailure { e ->
            _uiState.update {
                it.copy(
                    isDuplicationChecking = false,
                    isDuplicationChecked = false,
                    nicknameErrorMessage = e.message ?: "네트워크 문제가 발생했어요"
                )
            }
        }

        checkCanStart()
    }

    private fun checkCanStart() {
        val state = _uiState.value
        val isReady = state.isNicknameValid &&
            state.nickname.isNotEmpty() &&
            state.isDuplicationChecked &&
            !state.isDuplicationChecking
        _uiState.update { it.copy(canStart = isReady) }
    }
}
