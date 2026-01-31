package com.gildongmu.dduru_android.presentation.feature.register.util

import com.gildongmu.dduru_android.presentation.feature.register.state.NicknameValidationResult

object NicknameValidator {

    private const val MAX_LENGTH = 12

    fun validate(nickname: String): NicknameValidationResult {
        return when {
            nickname.isBlank() -> {
                NicknameValidationResult.EMPTY
            }
            nickname.length > MAX_LENGTH -> {
                NicknameValidationResult.TOO_LONG
            }
            else -> {
                NicknameValidationResult.VALID
            }
        }
    }
}