package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipationContactResponse(
    @SerialName("participationId") val participationId: Long,
    @SerialName("participantUserId") val participantUserId: Long? = null,
    @SerialName("privateRoomId") val privateRoomId: Long,
    @SerialName("status") val status: String? = null
)
