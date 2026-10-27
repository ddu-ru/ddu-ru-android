package com.gildongmu.dduru.presentation.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.domain.entity.mypage.MyProfile
import com.gildongmu.dduru.domain.register.nickname.repository.NicknameRepository
import com.gildongmu.dduru.domain.register.profile.model.ProfileImageType
import com.gildongmu.dduru.domain.register.profile.model.ProfileUpdateModel
import com.gildongmu.dduru.domain.register.profile.repository.ProfileRepository
import com.gildongmu.dduru.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MAX_BIO_LENGTH = 60

data class ProfileEditUiState(
    val nickname: String = "",
    val bio: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val imageType: ProfileImageType = ProfileImageType.DEFAULT,
    val profileImageUrl: String? = null,
    val bgColorId: Int? = null,
    val initialNickname: String = "",
    val initialBio: String = "",
    val isSaving: Boolean = false,
    val showLeaveConfirmDialog: Boolean = false
) {
    val isChanged: Boolean
        get() = nickname != initialNickname || bio != initialBio

    val isSaveEnabled: Boolean
        get() = nickname.isNotBlank() && isChanged && !isSaving
}

sealed interface ProfileEditSideEffect {
    data object NavigateBack : ProfileEditSideEffect
    data class ShowMessage(val message: String) : ProfileEditSideEffect
}

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val profileRepository: ProfileRepository,
    private val nicknameRepository: NicknameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProfileEditUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<ProfileEditUiState>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ProfileEditSideEffect>()
    val sideEffect: SharedFlow<ProfileEditSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            myPageRepository.getMyProfile()
                .onSuccess { _uiState.value = UiState.Success(it.toUiState()) }
                .onFailure { _uiState.value = UiState.Failure }
        }
    }

    fun onNicknameChange(nickname: String) {
        updateData { it.copy(nickname = nickname) }
    }

    fun onBioChange(bio: String) {
        updateData { it.copy(bio = bio.take(MAX_BIO_LENGTH)) }
    }

    fun onRandomNicknameClick() {
        viewModelScope.launch {
            nicknameRepository.getRandomNickname()
                .onSuccess { nickname -> updateData { it.copy(nickname = nickname) } }
                .onFailure { _sideEffect.emit(ProfileEditSideEffect.ShowMessage("랜덤 닉네임을 불러오지 못했어요.")) }
        }
    }

    fun onBackClick() {
        val state = (_uiState.value as? UiState.Success)?.data
        if (state?.isChanged == true) {
            updateData { it.copy(showLeaveConfirmDialog = true) }
        } else {
            viewModelScope.launch { _sideEffect.emit(ProfileEditSideEffect.NavigateBack) }
        }
    }

    fun onLeaveConfirmDismiss() {
        updateData { it.copy(showLeaveConfirmDialog = false) }
    }

    fun onLeaveConfirm() {
        updateData { it.copy(showLeaveConfirmDialog = false) }
        viewModelScope.launch { _sideEffect.emit(ProfileEditSideEffect.NavigateBack) }
    }

    fun onSaveClick() {
        val state = (_uiState.value as? UiState.Success)?.data ?: return
        if (!state.isSaveEnabled) return

        viewModelScope.launch {
            updateData { it.copy(isSaving = true) }
            profileRepository.updateProfile(
                ProfileUpdateModel(
                    nickname = state.nickname.trim(),
                    profileImageType = state.imageType,
                    uploadedImageUrl = state.profileImageUrl.takeIf { state.imageType == ProfileImageType.UPLOADED },
                    bgColorId = state.bgColorId.takeIf { state.imageType == ProfileImageType.AVATAR },
                    bio = state.bio.trim().ifBlank { null }
                )
            ).onSuccess {
                updateData {
                    it.copy(isSaving = false, initialNickname = it.nickname, initialBio = it.bio)
                }
                _sideEffect.emit(ProfileEditSideEffect.ShowMessage("프로필을 저장했어요."))
                _sideEffect.emit(ProfileEditSideEffect.NavigateBack)
            }.onFailure {
                updateData { it.copy(isSaving = false) }
                _sideEffect.emit(ProfileEditSideEffect.ShowMessage("저장에 실패했어요. 다시 시도해 주세요."))
            }
        }
    }

    private fun updateData(transform: (ProfileEditUiState) -> ProfileEditUiState) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: return@update state
            UiState.Success(transform(data))
        }
    }
}

private fun MyProfile.toUiState() = ProfileEditUiState(
    nickname = nickname,
    bio = bio.orEmpty(),
    birthDate = birthday?.replace("-", ".").orEmpty(),
    gender = when (gender) {
        "M" -> "남성"
        "F" -> "여성"
        else -> ""
    },
    phoneNumber = phoneNumber?.toPhoneNumberText().orEmpty(),
    imageType = ProfileImageType.entries.firstOrNull { it.apiValue == profileImageType } ?: ProfileImageType.DEFAULT,
    profileImageUrl = profileImageUrl,
    bgColorId = bgColorId,
    initialNickname = nickname,
    initialBio = bio.orEmpty()
)

private fun String.toPhoneNumberText(): String =
    if (length == 11) "${substring(0, 3)}-${substring(3, 7)}-${substring(7)}" else this
