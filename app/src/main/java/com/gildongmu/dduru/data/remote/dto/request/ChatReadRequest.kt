package com.gildongmu.dduru.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ChatReadRequest(
    val lastReadMessageId: Long
)
