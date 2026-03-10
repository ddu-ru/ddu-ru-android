package com.gildongmu.dduru.presentation.feature.register.util

import com.gildongmu.dduru.presentation.feature.register.state.PhoneValidationResult

object PhoneValidator {
    fun validate(phone: String): PhoneValidationResult {
        return when {
            phone.isBlank() -> {
                PhoneValidationResult.EMPTY
            }
            !phone.all { it.isDigit() } -> {
                PhoneValidationResult.NOT_DIGITS
            }
            phone.length != 11 -> {
                PhoneValidationResult.INVALID_LENGTH
            }
            !phone.startsWith("010") && !phone.startsWith("011") &&
                !phone.startsWith("016") && !phone.startsWith("017") &&
                !phone.startsWith("018") && !phone.startsWith("019") -> {
                PhoneValidationResult.INVALID_FORMAT
            }
            else -> {
                PhoneValidationResult.VALID
            }
        }
    }
}
