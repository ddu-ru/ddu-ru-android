package com.gildongmu.dduru.domain.nickname

object NicknameErrorMapper {
    fun toMessage(status: Int?): String {
        return when (status) {
            400 -> "닉네임을 확인해주세요."
            401 -> "인증되지 않은 사용자입니다."
            409 -> "이미 사용 중인 닉네임입니다."
            else -> "닉네임을 확인해주세요."
        }
    }
}
