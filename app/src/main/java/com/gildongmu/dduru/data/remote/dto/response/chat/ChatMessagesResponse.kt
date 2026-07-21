package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessagesResponse(
    val roomInfo: ChatRoomInfoResponse,
    val page: ChatMessagesPageResponse,
    val messages: List<MessageItemResponse>
)
