package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class MessageEntity(
    val messageId: Long,
    val messageType: MessageType,
    val sender: MessageSenderEntity?,
    val isMine: Boolean,
    val content: String?,
    val images: List<MessageImageEntity>,
    val systemMessage: SystemMessageEntity?,
    val unreadCount: Int?,
    val createdAt: LocalDateTime
)
