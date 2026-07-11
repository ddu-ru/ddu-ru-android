package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class ChatRoomLastMessage(
    val messageId: Long,
    val messageType: MessageType,
    val content: String?,
    val senderId: Long?,
    val senderNickname: String?,
    val createdAt: LocalDateTime
)
