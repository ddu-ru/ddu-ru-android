package com.gildongmu.dduru.data.register.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationSendResponse(
    val expiresAt: String
)
