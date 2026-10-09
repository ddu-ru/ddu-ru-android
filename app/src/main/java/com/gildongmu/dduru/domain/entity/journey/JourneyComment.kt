package com.gildongmu.dduru.domain.entity.journey

data class JourneyComment(
    val commentId: Long,
    val journeyPostId: Long,
    val author: JourneyAuthor,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val isAuthor: Boolean
)

data class JourneyCommentList(
    val journeyPostId: Long,
    val commentCount: Int,
    val comments: List<JourneyComment>
)
