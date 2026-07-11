package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class Message(
    val messageId: Long,
    val messageType: MessageType,
    val sender: MessageSender?,
    val isMine: Boolean,
    val content: String?,
    val images: List<MessageImage>,
    val systemMessage: SystemMessage?,
    val unreadCount: Int?,
    val createdAt: LocalDateTime
)
