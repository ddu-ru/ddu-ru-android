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
import com.gildongmu.dduru.domain.entity.chat.ChatRoomListResult
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
import kotlinx.collections.immutable.toImmutableList

class ChatRoomRepositoryImpl @Inject constructor(
    private val chatRoomService: ChatRoomService
) : ChatRoomRepository {

    override suspend fun getChatRooms(roomType: String?, cursor: String?): Result<ChatRoomListResult> {
        return try {
            val response = chatRoomService.getChatRooms(roomType = roomType, cursor = cursor)
            if (response.status != 200) {
                return Result.failure(
                    when (response.status) {
                        403 -> Exception("FORBIDDEN")
                        404 -> Exception("NOT_FOUND")
                        else -> Exception("SERVER_ERROR")
                    }
                )
            }
            val data = response.data ?: return Result.failure(Exception("OTHER"))
            Result.success(
                ChatRoomListResult(
                    chatRooms = data.chatRooms.map { it.toEntity() },
                    nextCursor = data.page.nextCursor,
                    hasNext = data.page.hasNext
                )
            )
        } catch (e: Exception) {
            if (e.message in listOf("FORBIDDEN", "NOT_FOUND", "SERVER_ERROR", "OTHER")) {
                Result.failure(e)
            } else {
                Result.failure(Exception("NETWORK_ERROR"))
            }
        }
    }

    override suspend fun getMessages(chatRoomId: Long, beforeMessageId: Long?): Result<ChatMessagesResultEntity> {
        return try {
            val response = chatRoomService.getMessages(chatRoomId = chatRoomId, beforeMessageId = beforeMessageId)
            if (response.status != 200) {
                return Result.failure(
                    when (response.status) {
                        403 -> Exception("FORBIDDEN")
                        404 -> Exception("NOT_FOUND")
                        else -> Exception("SERVER_ERROR")
                    }
                )
            }
            val data = response.data ?: return Result.failure(Exception("OTHER"))
            Result.success(
                ChatMessagesResultEntity(
                    roomInfo = data.roomInfo.toEntity(),
                    messages = data.messages.map { it.toEntity() }.toImmutableList(),
                    hasNext = data.page.hasNext,
                    nextCursor = data.page.nextCursor
                )
            )
        } catch (e: Exception) {
            if (e.message in listOf("FORBIDDEN", "NOT_FOUND", "SERVER_ERROR", "OTHER")) {
                Result.failure(e)
            } else {
                Result.failure(Exception("NETWORK_ERROR"))
            }
        }
    }

    override suspend fun markAsRead(chatRoomId: Long, lastReadMessageId: Long): Result<Unit> {
        return try {
            val response = chatRoomService.markAsRead(
                chatRoomId = chatRoomId,
                request = ChatReadRequest(lastReadMessageId = lastReadMessageId)
            )
            if (response.status != 200) {
                return Result.failure(
                    when (response.status) {
                        403 -> Exception("FORBIDDEN")
                        404 -> Exception("NOT_FOUND")
                        else -> Exception("SERVER_ERROR")
                    }
                )
            }
            Result.success(Unit)
        } catch (e: Exception) {
            if (e.message in listOf("FORBIDDEN", "NOT_FOUND", "SERVER_ERROR", "OTHER")) {
                Result.failure(e)
            } else {
                Result.failure(Exception("NETWORK_ERROR"))
            }
        }
    }

    override suspend fun createPrivateChatRoom(postId: Long): Result<Long> {
        return try {
            val response = chatRoomService.createPrivateChatRoom(postId = postId)
            if (response.status != 200 && response.status != 201) {
                return Result.failure(
                    when (response.status) {
                        403 -> Exception("FORBIDDEN")
                        404 -> Exception("NOT_FOUND")
                        else -> Exception("SERVER_ERROR")
                    }
                )
            }
            val data = response.data ?: return Result.failure(Exception("OTHER"))
            Result.success(data.roomId)
        } catch (e: Exception) {
            if (e.message in listOf("FORBIDDEN", "NOT_FOUND", "SERVER_ERROR", "OTHER")) {
                Result.failure(e)
            } else {
                Result.failure(Exception("NETWORK_ERROR"))
            }
        }
    }

    private fun ChatRoomResponse.toEntity(): ChatRoomEntity = ChatRoomEntity(
        chatRoomId = chatRoomId,
        roomType = ChatRoomType.entries.firstOrNull { it.name == roomType } ?: ChatRoomType.PRIVATE,
        status = ChatRoomStatus.entries.firstOrNull { it.name == status } ?: ChatRoomStatus.ACTIVE,
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
            messageType = MessageType.entries.firstOrNull { it.name == messageType } ?: MessageType.TEXT,
            content = content,
            senderId = senderId,
            senderNickname = senderNickname,
            createdAt = createdAt.parseDateTime()
        )

    private fun ChatRoomInfoResponse.toEntity(): ChatRoomInfoEntity = ChatRoomInfoEntity(
        chatRoomId = chatRoomId,
        roomType = ChatRoomType.entries.firstOrNull { it.name == roomType } ?: ChatRoomType.PRIVATE,
        isActive = isActive,
        postTitle = postTitle,
        opponentNickname = opponentNickname,
        journeyTitle = journeyTitle,
        memberCount = memberCount
    )

    private fun MessageItemResponse.toEntity(): MessageEntity = MessageEntity(
        messageId = messageId,
        messageType = MessageType.entries.firstOrNull { it.name == messageType } ?: MessageType.TEXT,
        sender = sender?.let {
            MessageSenderEntity(userId = it.userId, nickname = it.nickname, isHost = it.isHost)
        },
        isMine = isMine,
        content = content,
        images = images.map { MessageImageEntity(imageUrl = it.imageUrl) },
        systemMessage = systemMessage?.let { system ->
            SystemMessageType.entries.firstOrNull { it.name == system.type }?.let { type ->
                SystemMessageEntity(
                    type = type,
                    displayText = system.displayText,
                    actorUserId = system.actorUserId,
                    inviteeUserId = system.inviteeUserId,
                    userId = system.userId,
                    targetUserId = system.targetUserId
                )
            }
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
