package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

data class ChatRoom(
    val chatRoomId: Long,
    val roomType: ChatRoomType,
    val status: ChatRoomStatus,
    val displayName: String,
    val postTitle: String?,
    val thumbnailUrl: String?,
    val postId: Long?,
    val journeyId: Long?,
    val participantCount: Int,
    val lastMessage: ChatRoomLastMessage?,
    val unreadCount: Long,
    val lastReadMessageId: Long?,
    val activityAt: LocalDateTime?,
    val createdAt: LocalDateTime
)
