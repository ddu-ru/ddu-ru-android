package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

enum class MessageType {
    TEXT,
    IMAGE,
    SYSTEM
}

enum class SystemMessageType {
    USER_INVITED,
    USER_LEFT,
    USER_KICKED,
    JOURNEY_CREATED
}

data class MessageSenderEntity(
    val userId: Long,
    val nickname: String,
    val isHost: Boolean
)

data class MessageImageEntity(
    val imageUrl: String
)

data class SystemMessageEntity(
    val type: SystemMessageType,
    val displayText: String,
    val actorUserId: Long?,
    val inviteeUserId: Long?,
    val userId: Long?,
    val targetUserId: Long?
)

data class MessageEntity(
    val messageId: Long,
    val messageType: MessageType,
    val sender: MessageSenderEntity?,
    val isMine: Boolean,
    val content: String?,
    val images: List<MessageImageEntity>,
    val systemMessage: SystemMessageEntity?,
    val unreadCount: Int,
    val createdAt: LocalDateTime
)
