package com.gildongmu.dduru.data.nickname.model

import kotlinx.serialization.Serializable

@Serializable
data class SanitizeNicknameResponse(
    val sanitizedNickname: String
)
