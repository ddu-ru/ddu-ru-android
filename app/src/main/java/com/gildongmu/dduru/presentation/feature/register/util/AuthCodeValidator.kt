package com.gildongmu.dduru.presentation.feature.register.util

import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult

object AuthCodeValidator {
    private const val AUTH_CODE_LENGTH = 6

    fun validate(code: String, isExpired: Boolean = false): AuthCodeValidationResult {
        return when {
            isExpired -> {
                AuthCodeValidationResult.EXPIRED
            }
            code.isBlank() -> {
                AuthCodeValidationResult.EMPTY
            }
            code.length != AUTH_CODE_LENGTH -> {
                AuthCodeValidationResult.INVALID_LENGTH
            }
            else -> {
                AuthCodeValidationResult.VALID
            }
        }
    }
}
