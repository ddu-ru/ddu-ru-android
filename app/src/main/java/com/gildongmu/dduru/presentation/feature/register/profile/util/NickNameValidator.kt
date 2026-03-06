package com.gildongmu.dduru.presentation.feature.register.profile.util

enum class NicknameValidationResult {
    VALID,
    EMPTY,
    TOO_SHORT,
    TOO_LONG,
    NVALID_CHARACTERS,
    CONTAINS_EMPTY,
}

object NicknameValidator {

    private const val MIN_LENGTH = 2
    private const val MAX_LENGTH = 12

    private val NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]+( [가-힣a-zA-Z0-9]+)*$".toRegex()

    fun validate(nickname: String): NicknameValidationResult {
        return when {
            nickname.isBlank() -> NicknameValidationResult.EMPTY

            nickname.length < MIN_LENGTH -> NicknameValidationResult.TOO_SHORT

            nickname.length > MAX_LENGTH -> NicknameValidationResult.TOO_LONG

            nickname.contains(" ") || !nickname.matches(NICKNAME_REGEX) ->
                NicknameValidationResult.NVALID_CHARACTERS

            nickname.contains(" ") -> NicknameValidationResult.CONTAINS_EMPTY

            else -> NicknameValidationResult.VALID
        }
    }
}

fun NicknameValidationResult.toMessage(): String {
    return when (this) {
        NicknameValidationResult.VALID -> ""
        NicknameValidationResult.EMPTY -> "닉네임을 입력해주세요."
        NicknameValidationResult.TOO_SHORT -> "닉네임은 2자 이상 입력해주세요."
        NicknameValidationResult.TOO_LONG -> "닉네임은 최대 12자까지 입력할 수 있어요."
        NicknameValidationResult.NVALID_CHARACTERS -> "한글, 영어, 숫자만 사용 가능해요."
        NicknameValidationResult.CONTAINS_EMPTY -> "닉네임은 공백을 포함할 수 없어요."
    }
}
