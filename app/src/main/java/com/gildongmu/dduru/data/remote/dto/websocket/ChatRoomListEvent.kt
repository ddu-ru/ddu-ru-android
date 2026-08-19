package com.gildongmu.dduru.data.remote.dto.websocket

import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomListEvent(
    @SerialName("eventType") val eventType: String,
    @SerialName("reason") val reason: String? = null,
    @SerialName("chatRoom") val chatRoom: ChatRoomResponse,
    @SerialName("occurredAt") val occurredAt: String
)
