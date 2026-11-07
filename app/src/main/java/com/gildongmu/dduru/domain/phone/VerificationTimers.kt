package com.gildongmu.dduru.domain.phone

data class VerificationTimers(
    val phoneNumber: String,
    val expiresAtMillis: Long?,
    val resendAvailableAtMillis: Long?
)
