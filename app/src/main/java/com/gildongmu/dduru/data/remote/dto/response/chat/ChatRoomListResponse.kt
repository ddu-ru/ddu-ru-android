package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomListResponse(
    val selectedRoomType: String,
    val chatRooms: List<ChatRoomResponse>,
    val page: ChatRoomPageResponse
)
