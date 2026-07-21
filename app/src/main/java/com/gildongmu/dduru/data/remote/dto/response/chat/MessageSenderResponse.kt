package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class MessageSenderResponse(
    val userId: Long,
    val nickname: String,
    val isHost: Boolean
)
