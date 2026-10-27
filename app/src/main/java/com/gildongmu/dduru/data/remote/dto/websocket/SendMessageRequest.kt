package com.gildongmu.dduru.data.remote.dto.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    @SerialName("messageType") val messageType: String,
    @SerialName("content") val content: String
)
