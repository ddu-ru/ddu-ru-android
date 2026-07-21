package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessagesPageResponse(
    val size: Int,
    val hasNext: Boolean,
    val nextCursor: Long? = null
)
