package com.gildongmu.dduru.domain.entity.chat

data class ChatMessagesResultEntity(
    val roomInfo: ChatRoomInfoEntity,
    val messages: List<MessageEntity>,
    val hasNext: Boolean,
    val nextCursor: Long?
)
