package com.gildongmu.dduru.presentation.feature.register.util

import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.PhoneValidationResult

object ValidationMessageMapper {
    fun PhoneValidationResult.toMessage(): String =
        when (this) {
            PhoneValidationResult.EMPTY -> "전화번호를 입력해주세요."
            PhoneValidationResult.NOT_DIGITS -> "숫자만 입력 가능해요."
            PhoneValidationResult.INVALID_LENGTH -> "전화번호를 정확히 입력해주세요."
            PhoneValidationResult.INVALID_FORMAT -> "전화번호를 정확히 입력해주세요."
            PhoneValidationResult.VALID -> ""
        }

    fun AuthCodeValidationResult.toMessage(): String =
        when (this) {
            AuthCodeValidationResult.EMPTY -> "인증번호를 입력해주세요."
            AuthCodeValidationResult.EXPIRED_TIME -> "인증 시간이 만료되었어요."
            AuthCodeValidationResult.VALID -> ""
        }
}
