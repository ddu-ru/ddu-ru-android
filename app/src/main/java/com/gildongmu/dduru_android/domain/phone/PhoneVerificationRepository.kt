package com.gildongmu.dduru_android.domain.phone

interface PhoneVerificationRepository {

    suspend fun sendVerificationCode(
        phoneNumber: String
    ): Result<String>

    suspend fun confirmVerificationCode(
        phoneNumber: String,
        verificationCode: String
    ): Result<Unit>
}