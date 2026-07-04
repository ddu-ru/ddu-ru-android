package com.gildongmu.dduru.domain.entity.chat

data class SystemMessage(
    val type: SystemMessageType,
    val displayText: String,
    val actorUserId: Long?,
    val inviteeUserId: Long?,
    val userId: Long?,
    val targetUserId: Long?
)
