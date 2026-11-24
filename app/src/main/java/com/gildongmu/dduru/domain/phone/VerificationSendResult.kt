package com.gildongmu.dduru.domain.phone

data class VerificationSendResult(
    val expiresAt: String,
    val resendAvailableAt: String?
)
