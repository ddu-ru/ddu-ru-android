package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomResponse(
    val chatRoomId: Long,
    val roomType: String,
    val status: String,
    val displayName: String,
    val postTitle: String? = null,
    val thumbnailUrl: String? = null,
    val postId: Long? = null,
    val journeyId: Long? = null,
    val participantCount: Int,
    val lastMessage: ChatRoomLastMessageResponse? = null,
    val unreadCount: Long,
    val lastReadMessageId: Long? = null,
    val activityAt: String? = null,
    val createdAt: String
)
