package com.gildongmu.dduru.presentation.feature.register.profile.util


enum class ProfileType {
    AVATAR, UPLOADED, DEFAULT
}

enum class surveyStatus {
    COMPLETED, SKIPPED
}


data class ProfileState(
    val isSurveyComplete: surveyStatus = surveyStatus.SKIPPED,

    val profileType: ProfileType = ProfileType.UPLOADED,
    val profileUploadedUrl: String? = null,
    val profileAvatarUrl: String? = null,

    val nickname: String = "",

    val text: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
    val isDuplicationChecked: Boolean = false,
    val isDuplicationChecking: Boolean = false,

    val introduction: String = "",

    val isNicknameValid: Boolean = true,
    val canStart: Boolean = false
)
