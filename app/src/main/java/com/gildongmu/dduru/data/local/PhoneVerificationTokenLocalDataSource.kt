package com.gildongmu.dduru.data.local

import kotlinx.coroutines.flow.Flow

interface VerificationTokenLocalDataSource {
    val verificationToken: Flow<String?>

    suspend fun saveVerificationToken(token: String)

    suspend fun clearVerificationToken()
}
