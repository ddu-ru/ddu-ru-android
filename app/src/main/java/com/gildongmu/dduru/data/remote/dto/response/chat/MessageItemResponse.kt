package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class MessageItemResponse(
    val messageId: Long,
    val messageType: String,
    val sender: MessageSenderResponse? = null,
    val isMine: Boolean,
    val content: String? = null,
    val images: List<MessageImageResponse> = emptyList(),
    val systemMessage: SystemMessageResponse? = null,
    val unreadCount: Int? = null,
    val createdAt: String
)
