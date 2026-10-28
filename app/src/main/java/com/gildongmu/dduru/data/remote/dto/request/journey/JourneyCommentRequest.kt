package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyCommentRequest(
    val content: String
)
