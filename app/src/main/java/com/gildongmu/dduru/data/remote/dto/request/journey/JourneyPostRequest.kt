package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyPostRequest(
    val content: String,
    val imageUrls: List<String>
)
