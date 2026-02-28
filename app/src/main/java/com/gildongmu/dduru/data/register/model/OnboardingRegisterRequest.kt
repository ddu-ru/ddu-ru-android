package com.gildongmu.dduru.data.register.model

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRegisterRequest(
    val gender: String,

    val phoneNumber: String,

    val birthday: String,

    val verificationToken: String
)
