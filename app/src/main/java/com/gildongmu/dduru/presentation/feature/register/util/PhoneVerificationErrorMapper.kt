package com.gildongmu.dduru.presentation.feature.register.util

import androidx.annotation.StringRes
import com.gildongmu.dduru.R
import com.gildongmu.dduru.domain.entity.ExceptionResponse

sealed interface PhoneVerificationError {
    data class Phone(@StringRes val messageRes: Int) : PhoneVerificationError
    data class AuthCode(@StringRes val messageRes: Int) : PhoneVerificationError
    data class Birth(@StringRes val messageRes: Int) : PhoneVerificationError
    data class Gender(@StringRes val messageRes: Int) : PhoneVerificationError
    data class Toast(@StringRes val titleRes: Int, @StringRes val messageRes: Int) : PhoneVerificationError
}

object PhoneVerificationErrorMapper {

    fun toError(error: ExceptionResponse?): PhoneVerificationError =
        when (error?.errorCode) {
            "INVALID_INPUT_VALUE" -> when (error.field) {
                "phoneNumber" -> PhoneVerificationError.Phone(R.string.phone_error_invalid_format)
                "code" -> PhoneVerificationError.AuthCode(R.string.auth_code_error_invalid_length)
                "birthday" -> PhoneVerificationError.Birth(R.string.birth_error_invalid)
                "gender" -> PhoneVerificationError.Gender(R.string.gender_error_invalid)
                else -> requestFailed()
            }
            "DUPLICATE_PHONE_NUMBER" -> PhoneVerificationError.Phone(R.string.phone_error_duplicate)
            "INVALID_AUTH_CODE" -> PhoneVerificationError.AuthCode(R.string.auth_code_error_mismatch)
            "TOO_MANY_REQUESTS" -> PhoneVerificationError.AuthCode(R.string.auth_code_error_too_many_requests)
            "VERIFICATION_NOT_FOUND" -> PhoneVerificationError.AuthCode(R.string.auth_code_error_not_found)
            "VERIFICATION_ATTEMPTS_EXCEEDED" ->
                PhoneVerificationError.AuthCode(R.string.auth_code_error_attempts_exceeded)
            "DAILY_SMS_LIMIT_EXCEEDED" -> PhoneVerificationError.AuthCode(R.string.auth_code_error_daily_limit)
            "SMS_SEND_FAILED", "SMS_PROVIDER_ERROR" -> PhoneVerificationError.Toast(
                titleRes = R.string.phone_toast_send_failed_title,
                messageRes = R.string.phone_toast_retry_later
            )
            else -> requestFailed()
        }

    fun toSendError(error: ExceptionResponse?): PhoneVerificationError =
        when (error?.errorCode) {
            "TOO_MANY_REQUESTS" -> PhoneVerificationError.Phone(R.string.auth_code_error_too_many_requests)
            "DAILY_SMS_LIMIT_EXCEEDED" -> PhoneVerificationError.Phone(R.string.auth_code_error_daily_limit)
            else -> toError(error)
        }

    private fun requestFailed() = PhoneVerificationError.Toast(
        titleRes = R.string.phone_toast_request_failed_title,
        messageRes = R.string.phone_toast_retry_later
    )
}
