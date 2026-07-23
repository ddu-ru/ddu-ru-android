package com.gildongmu.dduru.domain.entity.chat

data class SystemMessageEntity(
    val type: SystemMessageType,
    val displayText: String,
    val actorUserId: Long?,
    val inviteeUserId: Long?,
    val userId: Long?,
    val targetUserId: Long?
)
