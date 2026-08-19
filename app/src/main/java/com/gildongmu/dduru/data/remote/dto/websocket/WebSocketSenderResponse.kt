package com.gildongmu.dduru.data.remote.dto.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketSenderResponse(
    val id: Long,
    val name: String,
    val profileImageInfo: ProfileImageInfo? = null,
    val isHost: Boolean = false
)

@Serializable
data class ProfileImageInfo(
    val type: String,
    val url: String,
    val bgColorId: Int? = null
)
