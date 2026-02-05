package com.gildongmu.dduru.data.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationConfirmRequest(
    val phoneNumber: String,
    val code: String
)

