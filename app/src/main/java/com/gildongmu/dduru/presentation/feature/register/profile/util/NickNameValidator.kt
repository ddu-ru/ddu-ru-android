package com.gildongmu.dduru.presentation.feature.register.profile.util

enum class NicknameValidationResult {
    VALID,
    EMPTY,
    TOO_SHORT,
    TOO_LONG,
    INVALID_CHARACTERS,
    CONTAINS_EMPTY
}

object NicknameValidator {

    private const val MIN_LENGTH = 2
    private const val MAX_LENGTH = 14

    private val NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]+$".toRegex()

    fun validate(nickname: String): NicknameValidationResult {
        return when {
            nickname.isBlank() -> NicknameValidationResult.EMPTY

            nickname.contains(" ") -> NicknameValidationResult.CONTAINS_EMPTY

            nickname.length < MIN_LENGTH -> NicknameValidationResult.TOO_SHORT

            nickname.length > MAX_LENGTH -> NicknameValidationResult.TOO_LONG

            !nickname.matches(NICKNAME_REGEX) ->
                NicknameValidationResult.INVALID_CHARACTERS

            else -> NicknameValidationResult.VALID
        }
    }
}

fun NicknameValidationResult.toMessage(): String {
    return when (this) {
        NicknameValidationResult.VALID -> ""
        NicknameValidationResult.EMPTY -> "닉네임을 입력해 주세요."
        NicknameValidationResult.TOO_SHORT,
        NicknameValidationResult.TOO_LONG -> "2~14자로 입력해 주세요."
        NicknameValidationResult.INVALID_CHARACTERS,
        NicknameValidationResult.CONTAINS_EMPTY -> "사용할 수 없는 문자가 포함되어 있어요."
    }
}
