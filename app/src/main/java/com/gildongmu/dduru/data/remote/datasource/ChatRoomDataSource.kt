package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.request.ChatReadRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatMessagesResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatReadResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomListResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.PrivateChatRoomResponse

interface ChatRoomDataSource {
    suspend fun getChatRooms(roomType: String?, cursor: String?): ApiResponse<ChatRoomListResponse>
    suspend fun getMessages(chatRoomId: Long, beforeMessageId: Long?): ApiResponse<ChatMessagesResponse>
    suspend fun markAsRead(chatRoomId: Long, request: ChatReadRequest): ApiResponse<ChatReadResponse>
    suspend fun createPrivateChatRoom(postId: Long): ApiResponse<PrivateChatRoomResponse>
}
