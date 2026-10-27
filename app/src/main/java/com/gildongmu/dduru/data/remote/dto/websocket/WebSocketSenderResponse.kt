package com.gildongmu.dduru.data.remote.dto.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketSenderResponse(
    val id: Long,
    val name: String? = null,
    val profileImageInfo: ProfileImageInfo? = null,
    val isHost: Boolean = false
)

@Serializable
data class ProfileImageInfo(
    val type: String? = null,
    val url: String? = null,
    val bgColorId: Long? = null
)
