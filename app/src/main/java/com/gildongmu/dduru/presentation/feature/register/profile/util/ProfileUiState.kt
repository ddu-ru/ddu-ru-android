package com.gildongmu.dduru.presentation.feature.register.profile.util

import com.gildongmu.dduru.domain.register.model.SurveyStatus

data class ProfileUiState(
    val surveyStatus: SurveyStatus = SurveyStatus.NOT_STARTED,
    val isImageUploading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isNicknameValid: Boolean = false,
    val nicknameErrorMessage: String? = null,
    val imageErrorMessage: String? = null,
    val submitErrorMessage: String? = null,
    val isDuplicationChecked: Boolean = false,
    val isDuplicationChecking: Boolean = false,
    val canStart: Boolean = false
)
