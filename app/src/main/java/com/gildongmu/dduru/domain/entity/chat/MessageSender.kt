package com.gildongmu.dduru.domain.entity.chat

data class MessageSender(
    val userId: Long,
    val nickname: String,
    val isHost: Boolean
)
