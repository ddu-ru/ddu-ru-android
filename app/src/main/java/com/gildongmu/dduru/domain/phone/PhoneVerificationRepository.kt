package com.gildongmu.dduru.domain.phone

interface PhoneVerificationRepository {

    suspend fun sendVerificationCode(
        phoneNumber: String
    ): Result<VerificationSendResult>

    suspend fun confirmVerificationCode(
        phoneNumber: String,
        verificationCode: String
    ): Result<Unit>

    suspend fun hasValidVerificationToken(phoneNumber: String): Boolean

    suspend fun clearVerificationToken()

    suspend fun saveVerificationTimers(timers: VerificationTimers)

    suspend fun getVerificationTimers(phoneNumber: String): VerificationTimers?

    suspend fun clearVerificationTimers()
}
