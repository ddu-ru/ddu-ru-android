package com.gildongmu.dduru.domain.entity.chat

import kotlinx.collections.immutable.ImmutableList

data class ChatMessagesResultEntity(
    val roomInfo: ChatRoomInfoEntity,
    val messages: ImmutableList<MessageEntity>,
    val hasNext: Boolean,
    val nextCursor: Long?
)
