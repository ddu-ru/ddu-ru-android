package com.gildongmu.dduru.data.remote.dto.websocket

import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomLastMessageResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomListEvent(
    @SerialName("eventType") val eventType: String,
    @SerialName("reason") val reason: String? = null,
    @SerialName("chatRoom") val chatRoom: ChatRoomListEventRoom,
    @SerialName("occurredAt") val occurredAt: String? = null
)

@Serializable
data class ChatRoomListEventRoom(
    val chatRoomId: Long,
    val roomType: String? = null,
    val status: String? = null,
    val displayName: String? = null,
    val postTitle: String? = null,
    val thumbnailUrl: String? = null,
    val postId: Long? = null,
    val journeyId: Long? = null,
    val participantCount: Int? = null,
    val lastMessage: ChatRoomLastMessageResponse? = null,
    val unreadCount: Long? = null,
    val lastReadMessageId: Long? = null,
    val activityAt: String? = null,
    val createdAt: String? = null
)
