package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipationApproveResponse(
    @SerialName("participationId") val participationId: Long,
    @SerialName("inviteeUserId") val inviteeUserId: Long? = null,
    @SerialName("groupRoomId") val groupRoomId: Long? = null,
    @SerialName("status") val status: String? = null
)
