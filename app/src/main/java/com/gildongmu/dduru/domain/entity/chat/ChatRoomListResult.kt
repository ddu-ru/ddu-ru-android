package com.gildongmu.dduru.domain.entity.chat

data class ChatRoomListResult(
    val chatRooms: List<ChatRoomEntity>,
    val nextCursor: String?,
    val hasNext: Boolean
)
