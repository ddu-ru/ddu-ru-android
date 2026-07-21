package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomPageResponse(
    val nextCursor: String? = null,
    val hasNext: Boolean
)
