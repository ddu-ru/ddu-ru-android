package com.gildongmu.dduru.presentation.feature.register.profile.viewmodel

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.R
import com.gildongmu.dduru.data.entity.image.ImagePresignedUrlUploader
import com.gildongmu.dduru.domain.register.model.SurveyStatus
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.domain.register.profile.model.ProfileUpdateModel
import com.gildongmu.dduru.domain.register.profile.repository.ProfileRepository
import com.gildongmu.dduru.domain.register.repository.OnboardingStatusRepository
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption
import com.gildongmu.dduru.presentation.feature.register.profile.state.ProfileFormState
import com.gildongmu.dduru.presentation.feature.register.profile.state.ProfileUiState
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidationResult
import com.gildongmu.dduru.presentation.feature.register.profile.util.NicknameValidator
import com.gildongmu.dduru.presentation.feature.register.profile.util.ProfileBackgroundColorList
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
    private val nicknameRepository: NicknameRepository,
    private val profileRepository: ProfileRepository,
    private val profileImageUploader: ImagePresignedUrlUploader,
    private val onboardingStatusRepository: OnboardingStatusRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(
        ProfileFormState(
            selectedBgColor = ProfileBackgroundColorList.first().color,
            savedBgColor = ProfileBackgroundColorList.first().color
        )
    )
    val formState = _formState.asStateFlow()

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            surveyStatus = SurveyStatus.NOT_STARTED
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _characterImageRes = MutableStateFlow(R.drawable.img_penguin_personallity)
    val characterImageRes = _characterImageRes.asStateFlow()

    val radioOptions: List<ProfileImageCropOption>
        get() = listOf(
            ProfileImageCropOption(_characterImageRes.value, ProfileEditOption.CHARACTER, 1.6f, 0.16f),
            ProfileImageCropOption(R.drawable.img_blank_profile, ProfileEditOption.CUSTOM)
        )

    val bgColors = ProfileBackgroundColorList
    private var nicknameCheckJob: Job? = null

    init {
        _formState.update {
            it.copy(
                selectedOption = radioOptions.last().type
            )
        }
        fetchSurveyStatus()
    }

    fun onNicknameChanged(newNickname: String) {
        val validationResult = NicknameValidator.validate(newNickname)
        val isValid = validationResult == NicknameValidationResult.VALID
        nicknameCheckJob?.cancel()

        _formState.update { currentState ->
            currentState.copy(
                nickname = newNickname
            )
        }
        _uiState.update {
            it.copy(
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

    fun onStartClick() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (!currentState.canStart || currentState.isSubmitting || currentState.isImageUploading) return@launch

            _uiState.update {
                it.copy(
                    isSubmitting = true,
                    imageErrorMessage = null,
                    submitErrorMessage = null
                )
            }

            val latestFormState = _formState.value
            val isAvatar = latestFormState.selectedOption == ProfileEditOption.CHARACTER
            val hasCustomImage = latestFormState.selectedImageUri != null
            val uploadedImageUrl = if (isAvatar || !hasCustomImage) {
                null
            } else {
                uploadSelectedImageIfNeeded(latestFormState.selectedImageUri).getOrElse { e ->
                    _uiState.update {
                        it.copy(
                            isImageUploading = false,
                            isSubmitting = false,
                            imageErrorMessage = e.message ?: "이미지 업로드에 실패했어요."
                        )
                    }
                    checkCanStart()
                    return@launch
                }
            }
            val payload = ProfileUpdateModel(
                nickname = latestFormState.nickname,
                profileImageType = when {
                    isAvatar -> "AVATAR"
                    hasCustomImage -> "UPLOADED"
                    else -> "DEFAULT"
                },
                uploadedImageUrl = uploadedImageUrl,
                bgColorId = if (isAvatar) {
                    bgColors.firstOrNull { it.color == latestFormState.savedBgColor }?.id ?: 0
                } else {
                    null
                },
                bio = latestFormState.introduction.takeIf { it.isNotBlank() }
            )
            val result = profileRepository.updateProfile(payload)

            result.onSuccess {
                _formState.update { it.copy(profileUploadedUrl = uploadedImageUrl) }
                _uiState.update {
                    it.copy(isSubmitting = false, isImageUploading = false)
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isImageUploading = false,
                        submitErrorMessage = e.message ?: "프로필 저장에 실패했어요."
                    )
                }
            }

            checkCanStart()
        }
    }

    fun onProfileOptionSelected(option: ProfileImageCropOption) {
        if (
            option.type == ProfileEditOption.CHARACTER &&
            _uiState.value.surveyStatus != SurveyStatus.COMPLETED
        ) {
            return
        }
        _formState.update { it.copy(selectedOption = option.type) }
        checkCanStart()
    }

    fun onIntroductionChanged(newText: String) {
        _formState.update { it.copy(introduction = newText) }
    }

    fun onImageSelected(uri: Uri?) {
        _formState.update {
            it.copy(
                selectedImageUri = uri,
                profileUploadedUrl = null
            )
        }
        _uiState.update {
            it.copy(
                isImageUploading = false,
                imageErrorMessage = null,
                submitErrorMessage = null
            )
        }
        checkCanStart()
    }

    fun onBgColorSelected(color: Color) {
        _formState.update { it.copy(selectedBgColor = color) }
    }

    fun commitBgColorSelection() {
        _formState.update { it.copy(savedBgColor = it.selectedBgColor) }
    }

    private fun fetchSurveyStatus() {
        viewModelScope.launch {
            onboardingStatusRepository.getSurveyStatus()
                .onSuccess { surveyStatus ->
                    _uiState.update { it.copy(surveyStatus = surveyStatus) }
                    if (surveyStatus != SurveyStatus.COMPLETED &&
                        _formState.value.selectedOption == ProfileEditOption.CHARACTER
                    ) {
                        _formState.update { it.copy(selectedOption = ProfileEditOption.CUSTOM) }
                    }
                    checkCanStart()
                }
                .onFailure {
                    _uiState.update { it.copy(surveyStatus = SurveyStatus.NOT_STARTED) }
                    if (_formState.value.selectedOption == ProfileEditOption.CHARACTER) {
                        _formState.update { it.copy(selectedOption = ProfileEditOption.CUSTOM) }
                    }
                    checkCanStart()
                }
        }
    }

    private fun scheduleDuplicationCheck(nickname: String) {
        nicknameCheckJob = viewModelScope.launch {
            delay(1500)
            checkNicknameDuplication(nickname)
        }
    }

    private suspend fun checkNicknameDuplication(nickname: String) {
        if (_formState.value.nickname != nickname) return
        if (NicknameValidator.validate(nickname) != NicknameValidationResult.VALID) return

        _uiState.update { it.copy(isDuplicationChecking = true, isDuplicationChecked = false) }

        val result = nicknameRepository.checkNicknameAvailability(nickname)
        if (_formState.value.nickname != nickname) return

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
        val uiState = _uiState.value
        val formState = _formState.value
        val isReady = uiState.isNicknameValid &&
            formState.nickname.isNotEmpty() &&
            uiState.isDuplicationChecked &&
            !uiState.isDuplicationChecking &&
            !uiState.isImageUploading &&
            !uiState.isSubmitting
        _uiState.update { it.copy(canStart = isReady) }
    }

    private suspend fun uploadSelectedImageIfNeeded(selectedImageUri: Uri?): Result<String?> {
        if (selectedImageUri == null) return Result.success(null)

        _uiState.update { it.copy(isImageUploading = true) }
        checkCanStart()

        return profileImageUploader.uploadImage(selectedImageUri)
            .map { uploadedUrl ->
                _formState.update { it.copy(profileUploadedUrl = uploadedUrl) }
                _uiState.update { it.copy(isImageUploading = false) }
                uploadedUrl
            }
            .onFailure {
                _formState.update { it.copy(profileUploadedUrl = null) }
                _uiState.update { it.copy(isImageUploading = false) }
            }
    }
}
