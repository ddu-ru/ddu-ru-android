package com.gildongmu.dduru.data.register.nickname.model

import kotlinx.serialization.Serializable

@Serializable
data class SanitizeNicknameResponse(
    val sanitizedNickname: String
)
