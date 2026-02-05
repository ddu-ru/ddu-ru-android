package com.gildongmu.dduru.domain.register

object OnboardingRegisterErrorMapper {
    fun toMessage(
        status: Int? = null,
    ): String {
        return when (status) {
            400 -> "입력값이 올바르지 않습니다."
            401 -> "인증이 필요합니다. 다시 로그인해주세요."
            404 -> "프로필 정보를 찾을 수 없습니다."
            409 -> "이미 사용 중인 정보가 있습니다."
            else -> "프로필 설정에 실패했어요."
        }
    }
}
