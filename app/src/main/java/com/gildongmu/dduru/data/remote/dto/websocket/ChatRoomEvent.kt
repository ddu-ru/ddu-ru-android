package com.gildongmu.dduru.data.remote.dto.websocket

import com.gildongmu.dduru.data.remote.dto.response.chat.MessageImageResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.SystemMessageResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomEvent(
    @SerialName("eventType") val eventType: String? = null,
    @SerialName("messageType") val messageType: String? = null,
    @SerialName("messageId") val messageId: Long? = null,
    @SerialName("sender") val sender: WebSocketSenderResponse? = null,
    @SerialName("isMine") val isMine: Boolean? = null,
    @SerialName("content") val content: String? = null,
    @SerialName("images") val images: List<MessageImageResponse> = emptyList(),
    @SerialName("systemMessage") val systemMessage: SystemMessageResponse? = null,
    @SerialName("unreadCount") val unreadCount: Int? = null,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("roomId") val roomId: Long? = null,
    @SerialName("readerUserId") val readerUserId: Long? = null,
    @SerialName("lastReadMessageId") val lastReadMessageId: Long? = null,
    @SerialName("readAt") val readAt: String? = null,
    @SerialName("userMessage") val userMessage: UserMessage? = null

)

@Serializable
data class UserMessage(
    val text: String? = null
)
