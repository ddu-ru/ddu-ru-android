package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class PrivateChatRoomResponse(
    val roomId: Long,
    val isCreated: Boolean
)
