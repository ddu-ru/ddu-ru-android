package com.gildongmu.dduru.data.remote.dto.response.chat

import kotlinx.serialization.Serializable

@Serializable
data class SystemMessageResponse(
    val type: String,
    val displayText: String,
    val actorUserId: Long? = null,
    val inviteeUserId: Long? = null,
    val userId: Long? = null,
    val targetUserId: Long? = null
)
