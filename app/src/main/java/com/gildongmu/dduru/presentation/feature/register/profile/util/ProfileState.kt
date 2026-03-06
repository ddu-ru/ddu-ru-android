package com.gildongmu.dduru.presentation.feature.register.profile.util

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.gildongmu.dduru.presentation.feature.register.profile.model.ProfileImageCropOption


enum class ProfileType {
    AVATAR, UPLOADED, DEFAULT
}

enum class surveyStatus {
    COMPLETED, SKIPPED
}


data class ProfileState(
    val isSurveyComplete: surveyStatus = surveyStatus.SKIPPED,

    val profileType: ProfileType = ProfileType.UPLOADED,
    val selectedOption: ProfileImageCropOption? = null,
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
)
