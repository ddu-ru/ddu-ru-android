package com.gildongmu.dduru.presentation.feature.register.util

import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult

object AuthCodeValidator {
    fun validate(
        code: String,
        timerSeconds: Int,
        isExpired: Boolean = false
    ): AuthCodeValidationResult {
        return when {
            timerSeconds <= 0 || isExpired -> {
                AuthCodeValidationResult.EXPIRED_TIME
            }
            code.isBlank() -> {
                AuthCodeValidationResult.EMPTY
            }
            else -> {
                AuthCodeValidationResult.VALID
            }
        }
    }
}
