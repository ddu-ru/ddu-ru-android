package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipationRetrieveResponse(
    @SerialName("participationId") val participationId: Long,
    @SerialName("userId") val userId: Long,
    @SerialName("userName") val userName: String,
    @SerialName("profileImage") val profileImage: String?,
    @SerialName("message") val message: String?,
    @SerialName("status") val status: String,
    @SerialName("appliedAt") val appliedAt: String,
    @SerialName("contactedAt") val contactedAt: String?,
    @SerialName("approvedAt") val approvedAt: String?,
    @SerialName("rejectedAt") val rejectedAt: String?,
    @SerialName("postId") val postId: Long,
    @SerialName("postTitle") val postTitle: String
)
