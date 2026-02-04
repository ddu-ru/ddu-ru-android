package com.gildongmu.dduru_android.data.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationConfirmRequest(
    val phoneNumber: String,
    val code: String
)

