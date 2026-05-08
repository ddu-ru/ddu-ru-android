package com.gildongmu.dduru.data.register.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationSendRequest(
    val phoneNumber: String
)
