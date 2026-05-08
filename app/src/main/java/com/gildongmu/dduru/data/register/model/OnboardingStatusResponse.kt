package com.gildongmu.dduru.data.register.model

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingStatusResponse(
    val isOnboardingCompleted: Boolean? = null,
    val isProfileCompleted: Boolean? = null,
    val surveyStatus: String? = null
)
