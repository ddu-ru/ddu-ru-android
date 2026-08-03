package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyParticipationResponse(
    @SerialName("participationId") val participationId: Long,
    @SerialName("postId") val postId: Long,
    @SerialName("postTitle") val postTitle: String,
    @SerialName("photoUrl") val photoUrl: String?,
    @SerialName("status") val status: String,
    @SerialName("appliedAt") val appliedAt: String,
    @SerialName("cancellable") val cancellable: Boolean,
    @SerialName("privateRoomId") val privateRoomId: Long?,
    @SerialName("groupRoomId") val groupRoomId: Long?
)
