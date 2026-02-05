package com.gildongmu.dduru.domain.phone


object PhoneVerificationErrorMapper {

    fun sendErrorMessage(status: Int? = null): String {
        return when (status) {
            409 -> "이미 가입된 전화번호입니다."
            429 -> "재발송 제한 시간이 지나지 않았습니다."
            500 -> "SMS 서비스에 일시적인 오류가 발생했습니다."
            else -> "인증번호 발송에 실패했어요."
        }
    }

    fun confirmErrorMessage(status: Int? = null): String {
        return when (status) {
            400 -> "인증번호가 일치하지 않습니다."
            404 -> "인증 정보를 찾을 수 없습니다."
            409 -> "이미 완료된 인증입니다."
            else -> "인증번호 확인에 실패했어요."
        }
    }
}
