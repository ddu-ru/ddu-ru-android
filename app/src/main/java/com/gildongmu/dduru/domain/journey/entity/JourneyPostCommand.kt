package com.gildongmu.dduru.domain.journey.entity

data class JourneyPostCommand(
    val content: String,
    val imageUrls: List<String>
)

data class JourneyCommentCommand(
    val content: String
)
