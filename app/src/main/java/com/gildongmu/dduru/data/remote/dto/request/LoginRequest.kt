package com.gildongmu.dduru.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val idToken: String
)
