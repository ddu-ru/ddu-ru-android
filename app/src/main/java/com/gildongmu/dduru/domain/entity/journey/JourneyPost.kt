package com.gildongmu.dduru.domain.entity.journey

data class JourneyAuthor(
    val userId: Long,
    val nickname: String,
    val profileImage: JourneyProfileImage,
    val isHost: Boolean,
    val gender: String,
    val ageGroup: String
)

data class JourneyProfileImage(
    val type: String,
    val url: String,
    val bgColorId: Int
)

data class JourneyPost(
    val journeyPostId: Long,
    val author: JourneyAuthor,
    val content: String,
    val imageUrls: List<String>,
    val isNotice: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val isAuthor: Boolean,
    val commentCount: Int
)

data class JourneyPostPage(
    val journeyId: Long,
    val posts: List<JourneyPost>,
    val nextCursor: Long?,
    val hasNext: Boolean,
    val size: Int
)

data class JourneyPostNotice(
    val journeyPostId: Long,
    val isNotice: Boolean
)
