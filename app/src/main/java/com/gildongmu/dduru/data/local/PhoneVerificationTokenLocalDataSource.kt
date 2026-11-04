package com.gildongmu.dduru.data.local

import com.gildongmu.dduru.domain.phone.VerificationTimers
import kotlinx.coroutines.flow.Flow

interface PhoneVerificationTokenLocalDataSource {
    val verificationToken: Flow<String?>
    val verificationTimers: Flow<VerificationTimers?>

    suspend fun saveVerificationToken(token: String)

    suspend fun clearVerificationToken()

    suspend fun saveVerificationTimers(timers: VerificationTimers)

    suspend fun clearVerificationTimers()
}
