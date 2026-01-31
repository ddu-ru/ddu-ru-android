package com.gildongmu.dduru_android.data.nickname.model

import kotlinx.serialization.Serializable

@Serializable
data class SanitizeNicknameResponse(
    val sanitizedNickname: String? = null,
    val errorCode: String? = null,
    val field: String? = null,
    val message: String? = null
)