package com.gildongmu.dduru.presentation.feature.register.profile.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidationResult
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidator
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileState
import com.gildongmu.dduru.presentation.feature.register.profile.util.SurveyStatus
import com.gildongmu.dduru.presentation.feature.register.profile.util.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileState(
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

        _uiState.update { currentState ->
            currentState.copy(
                nickname = newNickname,
                nicknameErrorMessage = if (newNickname.isEmpty()) null else validationResult.toMessage(),
                isNicknameValid = (validationResult == NicknameValidationResult.VALID)
            )
        }

        checkCanStart()
    }

    fun onRandomNickname() {
        val adjectives = listOf("즐거운", "씩씩한", "똑똑한", "배고픈", "신비로운")
        val nouns = listOf("펭귄", "뚜르", "모험가", "탐험대", "친구")

        val randomName = "${adjectives.random()} ${nouns.random()}"

        onNicknameChanged(randomName)
    }

    fun onProfileOptionSelected(option: ProfileImageCropOption) {
        if (option.type == ProfileEditOption.CHARACTER && _uiState.value.isSurveyComplete == SurveyStatus.SKIPPED) return
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

    private fun checkCanStart() {
        val state = _uiState.value
        val isReady = state.isNicknameValid && state.nickname.isNotEmpty()
        _uiState.update { it.copy(canStart = isReady) }
    }
}
