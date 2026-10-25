package com.gildongmu.dduru.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenRegisterRequest(
    val token: String,
    val deviceType: String = DEVICE_TYPE_AOS
)

@Serializable
data class FcmTokenDeleteRequest(
    val token: String
)

private const val DEVICE_TYPE_AOS = "AOS"
