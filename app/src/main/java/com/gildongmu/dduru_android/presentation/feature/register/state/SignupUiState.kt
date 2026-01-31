package com.gildongmu.dduru_android.presentation.feature.register.state

enum class Gender {
    MALE, FEMALE
}
enum class SignupStep {
    BIRTH,
    GENDER,
    NICKNAME,
    PHONE,
    AUTH,
    DONE
}

enum class PhoneValidationResult {
    VALID,
    EMPTY,
    NOT_DIGITS,
    INVALID_LENGTH,
}

enum class AuthCodeValidationResult {
    VALID,
    EMPTY,
    EXPIRED_TIME,
}

data class CompletedItem(
    val label: String,
    val value: String
)


data class SignupUiState(
    val step: SignupStep = SignupStep.BIRTH,

    val completedItems: List<CompletedItem> = emptyList(),

    val year: Int? = null,
    val month: Int? = null,
    val day: Int? = null,

    val gender: Gender? = null,

    val nickname: String = "",

    val phoneNumber: String = "",
    val isPhoneRequested: Boolean = false,
    val phoneValidation: PhoneValidationResult = PhoneValidationResult.EMPTY,
    val phoneErrorMessage: String = "",

    val authCode: String = "",
    val isAuthVerified: Boolean = false,
    val authCodeValidation : AuthCodeValidationResult = AuthCodeValidationResult.EMPTY,
    val authCodeErrorMessage: String = "",
    val authTimerSeconds: Int = 0,
)
