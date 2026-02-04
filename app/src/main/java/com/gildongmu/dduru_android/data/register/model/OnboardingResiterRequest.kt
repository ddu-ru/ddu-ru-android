package com.gildongmu.dduru_android.data.register.model

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRegisterRequest(
    val nickname: String,

    val gender: String,

    val phoneNumber: String,

    val birthday: String,

    val verificationToken: String
)
