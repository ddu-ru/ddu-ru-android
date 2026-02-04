package com.gildongmu.dduru_android.data.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationSendResponse(
    val expiresAt: String,
)
