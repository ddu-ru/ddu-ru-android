package com.gildongmu.dduru.data.remote.dto.response.participation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantProfileImageResponse(
    @SerialName("type") val type: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("bgColorId") val bgColorId: Long? = null
)
