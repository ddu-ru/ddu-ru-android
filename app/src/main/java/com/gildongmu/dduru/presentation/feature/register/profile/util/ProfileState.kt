package com.gildongmu.dduru.presentation.feature.register.profile.util

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileEditOption
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption

enum class ProfileType {
    AVATAR, UPLOADED, DEFAULT
}

enum class SurveyStatus {
    COMPLETED, SKIPPED
}

data class ProfileState(
    val isSurveyComplete: SurveyStatus = SurveyStatus.SKIPPED,

    val selectedOption: ProfileEditOption = ProfileEditOption.CUSTOM,
    val selectedImageUri: Uri? = null,

    val selectedBgColor: Color,
    val savedBgColor: Color,

    val profileUploadedUrl: String? = null,
    val profileAvatarUrl: String? = null,

    val nickname: String = "",
    val isNicknameValid: Boolean = false,
    val nicknameErrorMessage: String? = null,
    val isDuplicationChecked: Boolean = false,
    val isDuplicationChecking: Boolean = false,

    val introduction: String = "",

    val canStart: Boolean = false
) {

    val profileType: ProfileType
        get() = when (selectedOption) {
            ProfileEditOption.CHARACTER -> ProfileType.AVATAR

            ProfileEditOption.CUSTOM ->
                if (selectedImageUri != null) {
                    ProfileType.UPLOADED
                } else {
                    ProfileType.DEFAULT
                }

            else -> ProfileType.DEFAULT
        }
}
