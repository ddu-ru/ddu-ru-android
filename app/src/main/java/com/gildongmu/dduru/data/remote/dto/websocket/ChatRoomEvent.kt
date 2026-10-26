package com.gildongmu.dduru.data.remote.dto.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomEvent(
    @SerialName("eventType") val eventType: String? = null,
    @SerialName("messageType") val messageType: String? = null,
    @SerialName("messageId") val messageId: Long? = null,
    @SerialName("roomId") val roomId: Long? = null,
    @SerialName("sender") val sender: WebSocketSenderResponse? = null,
    @SerialName("userMessage") val userMessage: UserMessage? = null,
    @SerialName("systemMessage") val systemMessage: WebSocketSystemMessage? = null,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("readerUserId") val readerUserId: Long? = null,
    @SerialName("lastReadMessageId") val lastReadMessageId: Long? = null,
    @SerialName("readAt") val readAt: String? = null
)

@Serializable
data class UserMessage(
    val text: String? = null,
    val imageUrl: String? = null
)

@Serializable
data class WebSocketSystemMessage(
    val type: String? = null,
    val actorUserId: Long? = null,
    val inviteeUserId: Long? = null,
    val userId: Long? = null,
    val targetUserId: Long? = null
)
