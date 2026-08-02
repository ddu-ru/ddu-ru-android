package com.gildongmu.dduru.data.remote.service

import com.gildongmu.dduru.data.remote.dto.request.ChatReadRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatMessagesResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatReadResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomListResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.PrivateChatRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatRoomService {

    @GET("api/v1/chat-rooms")
    suspend fun getChatRooms(
        @Query("roomType") roomType: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("size") size: Int? = null
    ): ApiResponse<ChatRoomListResponse>

    @GET("api/v1/chat-rooms/{chatRoomId}/messages")
    suspend fun getMessages(
        @Path("chatRoomId") chatRoomId: Long,
        @Query("beforeMessageId") beforeMessageId: Long? = null,
        @Query("size") size: Int? = null
    ): ApiResponse<ChatMessagesResponse>

    @PATCH("api/v1/chat-rooms/{chatRoomId}/read")
    suspend fun markAsRead(
        @Path("chatRoomId") chatRoomId: Long,
        @Body request: ChatReadRequest
    ): ApiResponse<ChatReadResponse>

    @POST("api/v1/posts/{postId}/chats/private")
    suspend fun createPrivateChatRoom(
        @Path("postId") postId: Long
    ): ApiResponse<PrivateChatRoomResponse>
}
