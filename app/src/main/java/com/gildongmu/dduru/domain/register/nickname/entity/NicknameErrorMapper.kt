package com.gildongmu.dduru.domain.register.nickname.entity

object NicknameErrorMapper {
    fun toMessage(status: Int?, errorCode: String? = null): String {
        return when (errorCode) {
            "NICKNAME_NOT_BLANK" -> "닉네임을 입력해 주세요."
            "NICKNAME_INVALID_LENGTH" -> "2~14자로 입력해 주세요."
            "NICKNAME_INVALID_CHARACTERS",
            "NICKNAME_CONSECUTIVE_SPACES",
            "NICKNAME_CONTAINS_EMOJI_OR_SYMBOL" -> "사용할 수 없는 문자가 포함되어 있어요."
            "NICKNAME_CONTAINS_BAD_WORD" -> "사용할 수 없는 단어가 포함되어 있어요."
            "NICKNAME_ALREADY_TAKEN" -> "이미 사용 중인 닉네임이에요."
            else -> when (status) {
                401 -> "인증되지 않은 사용자입니다."
                409 -> "이미 사용 중인 닉네임이에요."
                else -> "닉네임을 확인해주세요."
            }
        }
    }
}
