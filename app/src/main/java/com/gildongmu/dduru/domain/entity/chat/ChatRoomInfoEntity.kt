package com.gildongmu.dduru.domain.entity.chat

data class ChatRoomInfoEntity(
    val chatRoomId: Long,
    val roomType: ChatRoomType,
    val isActive: Boolean,
    val postTitle: String?,
    val opponentNickname: String?,
    val journeyTitle: String?,
    val memberCount: Int?
)
