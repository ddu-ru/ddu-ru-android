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
            else -> {
                PhoneValidationResult.VALID
            }
        }
    }
}
