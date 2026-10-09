package com.gildongmu.dduru.domain.entity.journey

data class JourneyPostCommand(
    val content: String,
    val imageUrls: List<String>
)

data class JourneyCommentCommand(
    val content: String
)
