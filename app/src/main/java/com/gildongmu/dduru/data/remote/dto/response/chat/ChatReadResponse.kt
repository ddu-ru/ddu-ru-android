package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatReadResponse(
    val chatRoomId: Long,
    val lastReadMessageId: Long,
    val updated: Boolean
)
