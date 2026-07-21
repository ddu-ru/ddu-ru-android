package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.chat.ChatMessagesResultEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomEntity

interface ChatRoomRepository {
    suspend fun getChatRooms(roomType: String? = null, cursor: String? = null): Result<List<ChatRoomEntity>>
    suspend fun getMessages(chatRoomId: Long, beforeMessageId: Long? = null): Result<ChatMessagesResultEntity>
    suspend fun markAsRead(chatRoomId: Long, lastReadMessageId: Long): Result<Unit>
    suspend fun createPrivateChatRoom(postId: Long): Result<Long>
}
