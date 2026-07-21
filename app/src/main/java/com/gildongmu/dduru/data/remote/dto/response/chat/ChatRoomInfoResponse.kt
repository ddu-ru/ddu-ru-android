package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomInfoResponse(
    val chatRoomId: Long,
    val roomType: String,
    val isActive: Boolean,
    val postTitle: String? = null,
    val opponentNickname: String? = null,
    val journeyTitle: String? = null,
    val memberCount: Int? = null
)
