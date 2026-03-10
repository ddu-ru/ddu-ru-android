package com.gildongmu.dduru.presentation.feature.register.state

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
    EXPIRED_TIME
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
    val phoneValidation: PhoneValidationResult = PhoneValidationResult.EMPTY,
    val phoneErrorMessage: String = "",

    val authCode: String = "",
    val isAuthVerified: Boolean = false,
    val authCodeValidation: AuthCodeValidationResult = AuthCodeValidationResult.EMPTY,
    val authCodeErrorMessage: String = "",
    val authTimerSeconds: Int = 0,

    val errorMessage: String? = null,

    val visibleSteps: List<SignupStep> = listOf(SignupStep.BIRTH),
    val bottomButton: SignupBottomButtonState = SignupBottomButtonState(),
    val timerText: String = "00:00"
)
