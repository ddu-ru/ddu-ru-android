package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomLastMessageResponse(
    val messageId: Long,
    val messageType: String,
    val content: String? = null,
    val senderId: Long? = null,
    val senderNickname: String? = null,
    val createdAt: String
)
