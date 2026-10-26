package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipationRetrieveResponse(
    @SerialName("participationId") val participationId: Long,
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("profileImage") val profileImage: ParticipantProfileImageResponse? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("status") val status: String,
    @SerialName("appliedAt") val appliedAt: String,
    @SerialName("contactedAt") val contactedAt: String? = null,
    @SerialName("approvedAt") val approvedAt: String? = null,
    @SerialName("rejectedAt") val rejectedAt: String? = null,
    @SerialName("postId") val postId: Long,
    @SerialName("postTitle") val postTitle: String
)
