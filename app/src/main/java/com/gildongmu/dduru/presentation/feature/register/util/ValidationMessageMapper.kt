package com.gildongmu.dduru.presentation.feature.register.util

import androidx.annotation.StringRes
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.PhoneValidationResult

object ValidationMessageMapper {
    @StringRes
    fun PhoneValidationResult.toMessageRes(): Int? =
        when (this) {
            PhoneValidationResult.EMPTY -> R.string.phone_error_empty
            PhoneValidationResult.NOT_DIGITS,
            PhoneValidationResult.INVALID_LENGTH,
            PhoneValidationResult.INVALID_FORMAT -> R.string.phone_error_invalid_format
            PhoneValidationResult.VALID -> null
        }

    @StringRes
    fun AuthCodeValidationResult.toMessageRes(): Int? =
        when (this) {
            AuthCodeValidationResult.EMPTY -> R.string.auth_code_error_empty
            AuthCodeValidationResult.INVALID_LENGTH -> R.string.auth_code_error_invalid_length
            AuthCodeValidationResult.EXPIRED -> R.string.auth_code_error_expired
            AuthCodeValidationResult.VALID -> null
        }
}
