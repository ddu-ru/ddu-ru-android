package com.gildongmu.dduru.presentation.feature.chat.util

import com.gildongmu.dduru.domain.entity.chat.ChatRoomLastMessageEntity
import com.gildongmu.dduru.domain.entity.chat.MessageType
import com.gildongmu.dduru.domain.entity.chat.SystemMessageType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private const val IMAGE_PREVIEW_TEXT = "사진을 보냈습니다."
private const val UNKNOWN_NICKNAME = "알 수 없는 사용자"

private val systemPayloadJson = Json { ignoreUnknownKeys = true }

fun systemMessageTypeOf(value: String?): SystemMessageType? =
    SystemMessageType.entries.firstOrNull { it.name == value }

fun ChatRoomLastMessageEntity.toPreviewText(): String =
    lastMessagePreviewText(messageType = messageType, content = content)

fun lastMessagePreviewText(messageType: MessageType, content: String?): String = when (messageType) {
    MessageType.TEXT -> content.orEmpty()
    MessageType.IMAGE -> IMAGE_PREVIEW_TEXT
    MessageType.SYSTEM -> systemMessageTypeOf(parseSystemType(content))?.previewText().orEmpty()
}

fun SystemMessageType.previewText(): String = when (this) {
    SystemMessageType.USER_INVITED -> "새로운 멤버가 참여했습니다."
    SystemMessageType.USER_LEFT -> "멤버가 채팅방을 나갔습니다."
    SystemMessageType.USER_KICKED -> "멤버가 채팅방에서 내보내졌습니다."
    SystemMessageType.ROOM_CLOSED -> "채팅방이 닫혔습니다."
}

fun SystemMessageType.displayText(nickname: String?): String {
    val name = nickname ?: UNKNOWN_NICKNAME
    return when (this) {
        SystemMessageType.USER_INVITED -> "$name 님이 그룹 채팅방에 참여했습니다."
        SystemMessageType.USER_LEFT -> "$name 님이 채팅방을 나갔습니다."
        SystemMessageType.USER_KICKED -> "$name 님이 채팅방에서 내보내졌습니다."
        SystemMessageType.ROOM_CLOSED -> "채팅방이 닫혔습니다."
    }
}

fun SystemMessageType.displayUserId(
    inviteeUserId: Long?,
    userId: Long?,
    targetUserId: Long?
): Long? = when (this) {
    SystemMessageType.USER_INVITED -> inviteeUserId
    SystemMessageType.USER_LEFT -> userId
    SystemMessageType.USER_KICKED -> targetUserId
    SystemMessageType.ROOM_CLOSED -> null
}

private fun parseSystemType(content: String?): String? = content?.let {
    runCatching {
        systemPayloadJson.parseToJsonElement(it).jsonObject["type"]?.jsonPrimitive?.content
    }.getOrNull()
}
