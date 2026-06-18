package com.gildongmu.dduru.domain.entity.chat

import java.time.LocalDateTime

enum class ChatRoomType {
    PRIVATE,
    GROUP
}

data class ChatRoomEntity(
    val chatRoomId: Long,
    val roomType: ChatRoomType,
    val isActive: Boolean,
    val postTitle: String,
    val opponentNickname: String,
    val journeyTitle: String?,
    val memberCount: Int,
    val lastMessage: String?,
    val lastMessageAt: LocalDateTime?,
    val unreadCount: Int
)
