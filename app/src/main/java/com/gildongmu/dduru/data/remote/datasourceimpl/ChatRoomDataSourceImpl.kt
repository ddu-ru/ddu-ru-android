package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.ChatRoomDataSource
import com.gildongmu.dduru.data.remote.dto.request.ChatReadRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatMessagesResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatReadResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomListResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.PrivateChatRoomResponse
import com.gildongmu.dduru.data.remote.service.ChatRoomService
import javax.inject.Inject

class ChatRoomDataSourceImpl @Inject constructor(
    private val chatRoomService: ChatRoomService
) : ChatRoomDataSource {

    override suspend fun getChatRooms(
        roomType: String?,
        cursor: String?
    ): ApiResponse<ChatRoomListResponse> =
        chatRoomService.getChatRooms(roomType = roomType, cursor = cursor)

    override suspend fun getMessages(
        chatRoomId: Long,
        beforeMessageId: Long?
    ): ApiResponse<ChatMessagesResponse> =
        chatRoomService.getMessages(chatRoomId = chatRoomId, beforeMessageId = beforeMessageId)

    override suspend fun markAsRead(
        chatRoomId: Long,
        request: ChatReadRequest
    ): ApiResponse<ChatReadResponse> =
        chatRoomService.markAsRead(chatRoomId = chatRoomId, request = request)

    override suspend fun createPrivateChatRoom(postId: Long): ApiResponse<PrivateChatRoomResponse> =
        chatRoomService.createPrivateChatRoom(postId = postId)
}
