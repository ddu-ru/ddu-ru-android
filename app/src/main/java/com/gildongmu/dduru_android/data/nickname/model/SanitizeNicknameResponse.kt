package com.gildongmu.dduru_android.data.nickname.model

import kotlinx.serialization.Serializable

@Serializable
data class SanitizeNicknameResponse(
    val sanitizedNickname: String,
)