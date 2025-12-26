package com.gildongmu.dduru_android.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)
