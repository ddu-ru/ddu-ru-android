package com.gildongmu.dduru.domain.journey.entity

data class JourneyPostCommand(
    val content: String,
    val imageUrls: List<String>
)

data class JourneyPostUpdateCommand(
    val content: String? = null,
    val imageUrls: List<String>? = null
)

data class JourneyCommentCommand(
    val content: String
)
