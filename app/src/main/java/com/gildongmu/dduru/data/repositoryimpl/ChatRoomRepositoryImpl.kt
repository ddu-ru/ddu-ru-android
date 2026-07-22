package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.request.ChatReadRequest
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomInfoResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomLastMessageResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.ChatRoomResponse
import com.gildongmu.dduru.data.remote.dto.response.chat.MessageItemResponse
import com.gildongmu.dduru.data.remote.service.ChatRoomService
import com.gildongmu.dduru.domain.entity.chat.ChatMessagesResultEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomInfoEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomLastMessageEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomStatus
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.domain.entity.chat.MessageEntity
import com.gildongmu.dduru.domain.entity.chat.MessageImageEntity
import com.gildongmu.dduru.domain.entity.chat.MessageSenderEntity
import com.gildongmu.dduru.domain.entity.chat.MessageType
import com.gildongmu.dduru.domain.entity.chat.SystemMessageEntity
import com.gildongmu.dduru.domain.entity.chat.SystemMessageType
import com.gildongmu.dduru.domain.repository.ChatRoomRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class ChatRoomRepositoryImpl @Inject constructor(
    private val chatRoomService: ChatRoomService
) : ChatRoomRepository {

    override suspend fun getChatRooms(roomType: String?, cursor: String?): Result<List<ChatRoomEntity>> {
        return try {
            val response = chatRoomService.getChatRooms(roomType = roomType, cursor = cursor)
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            val data = response.data ?: return Result.failure(Exception("Response data is null"))
            Result.success(data.chatRooms.map { it.toEntity() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMessages(chatRoomId: Long, beforeMessageId: Long?): Result<ChatMessagesResultEntity> {
        return try {
            val response = chatRoomService.getMessages(
                chatRoomId = chatRoomId,
                beforeMessageId = beforeMessageId
            )
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            val data = response.data ?: return Result.failure(Exception("Response data is null"))
            Result.success(
                ChatMessagesResultEntity(
                    roomInfo = data.roomInfo.toEntity(),
                    messages = data.messages.map { it.toEntity() },
                    hasNext = data.page.hasNext,
                    nextCursor = data.page.nextCursor
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(chatRoomId: Long, lastReadMessageId: Long): Result<Unit> {
        return try {
            val response = chatRoomService.markAsRead(
                chatRoomId = chatRoomId,
                request = ChatReadRequest(lastReadMessageId = lastReadMessageId)
            )
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createPrivateChatRoom(postId: Long): Result<Long> {
        return try {
            val response = chatRoomService.createPrivateChatRoom(postId = postId)
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            val data = response.data ?: return Result.failure(Exception("Response data is null"))
            Result.success(data.roomId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun ChatRoomResponse.toEntity(): ChatRoomEntity = ChatRoomEntity(
        chatRoomId = chatRoomId,
        roomType = ChatRoomType.valueOf(roomType),
        status = ChatRoomStatus.valueOf(status),
        displayName = displayName,
        postTitle = postTitle,
        thumbnailUrl = thumbnailUrl,
        postId = postId,
        journeyId = journeyId,
        participantCount = participantCount,
        lastMessage = lastMessage?.toEntity(),
        unreadCount = unreadCount,
        lastReadMessageId = lastReadMessageId,
        activityAt = activityAt?.parseDateTime(),
        createdAt = createdAt.parseDateTime()
    )

    private fun ChatRoomLastMessageResponse.toEntity(): ChatRoomLastMessageEntity =
        ChatRoomLastMessageEntity(
            messageId = messageId,
            messageType = MessageType.valueOf(messageType),
            content = content,
            senderId = senderId,
            senderNickname = senderNickname,
            createdAt = createdAt.parseDateTime()
        )

    private fun ChatRoomInfoResponse.toEntity(): ChatRoomInfoEntity = ChatRoomInfoEntity(
        chatRoomId = chatRoomId,
        roomType = ChatRoomType.valueOf(roomType),
        isActive = isActive,
        postTitle = postTitle,
        opponentNickname = opponentNickname,
        journeyTitle = journeyTitle,
        memberCount = memberCount
    )

    private fun MessageItemResponse.toEntity(): MessageEntity = MessageEntity(
        messageId = messageId,
        messageType = MessageType.valueOf(messageType),
        sender = sender?.let {
            MessageSenderEntity(userId = it.userId, nickname = it.nickname, isHost = it.isHost)
        },
        isMine = isMine,
        content = content,
        images = images.map { MessageImageEntity(imageUrl = it.imageUrl) },
        systemMessage = systemMessage?.let {
            SystemMessageEntity(
                type = SystemMessageType.valueOf(it.type),
                displayText = it.displayText,
                actorUserId = it.actorUserId,
                inviteeUserId = it.inviteeUserId,
                userId = it.userId,
                targetUserId = it.targetUserId
            )
        },
        unreadCount = unreadCount,
        createdAt = createdAt.parseDateTime()
    )

    private fun String.parseDateTime(): LocalDateTime =
        try {
            ZonedDateTime.parse(this)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()
        } catch (e: Exception) {
            LocalDateTime.parse(this)
        }
}
