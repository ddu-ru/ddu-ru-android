package com.gildongmu.dduru.data.remote.dto.response.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyAuthorResponse(
    val userId: Long,
    val nickname: String,
    val profileImage: JourneyProfileImageResponse,
    val isHost: Boolean,
    val gender: String,
    val ageGroup: String
)

@Serializable
data class JourneyProfileImageResponse(
    val type: String,
    val url: String,
    val bgColorId: Int
)
