package com.gildongmu.dduru.data.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationConfirmResponse(
    val status: String,
    val token: String
)
