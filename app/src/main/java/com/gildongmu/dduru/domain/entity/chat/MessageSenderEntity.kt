package com.gildongmu.dduru.domain.entity.chat

data class MessageSenderEntity(
    val userId: Long,
    val nickname: String,
    val isHost: Boolean
)
