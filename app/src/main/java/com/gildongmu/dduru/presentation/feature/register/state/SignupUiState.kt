package com.gildongmu.dduru.presentation.feature.register.state

import androidx.annotation.StringRes

enum class Gender {
    MALE,
    FEMALE
}

enum class SignupStep {
    BIRTH,
    GENDER,
    PHONE,
    AUTH,
    AUTH_VERIFICATION,
    DONE
}

enum class PhoneValidationResult {
    VALID,
    EMPTY,
    NOT_DIGITS,
    INVALID_LENGTH,
    INVALID_FORMAT
}

enum class AuthCodeValidationResult {
    VALID,
    EMPTY,
    INVALID_LENGTH,
    EXPIRED
}

data class CompletedItem(
    val label: String,
    val value: String
)

data class SignupBottomButtonState(
    val visible: Boolean = false,
    val text: String = "",
    val enabled: Boolean = false
)

data class SignupToast(
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int
)

data class SignupUiState(
    val step: SignupStep = SignupStep.BIRTH,

    val isLoading: Boolean = false,

    val completedItems: List<CompletedItem> = emptyList(),

    val year: Int? = null,
    val month: Int? = null,
    val day: Int? = null,

    val gender: Gender? = null,

    val phoneNumber: String = "",
    val isPhoneRequested: Boolean = false,
    val isSendingCode: Boolean = false,
    val phoneValidation: PhoneValidationResult = PhoneValidationResult.EMPTY,
    @StringRes val phoneErrorRes: Int? = null,

    val authCode: String = "",
    val isAuthVerified: Boolean = false,
    val isConfirmingCode: Boolean = false,
    val authCodeValidation: AuthCodeValidationResult = AuthCodeValidationResult.EMPTY,
    @StringRes val authCodeErrorRes: Int? = null,
    @StringRes val authCodeInfoRes: Int? = null,
    val authTimerSeconds: Int = 0,
    val resendRemainingSeconds: Int = 0,

    @StringRes val birthErrorRes: Int? = null,
    @StringRes val genderErrorRes: Int? = null,

    val toast: SignupToast? = null,
    val isLoginExpired: Boolean = false,

    val visibleSteps: List<SignupStep> = listOf(SignupStep.BIRTH),
    val bottomButton: SignupBottomButtonState = SignupBottomButtonState(),
    val timerText: String = "00:00"
)
