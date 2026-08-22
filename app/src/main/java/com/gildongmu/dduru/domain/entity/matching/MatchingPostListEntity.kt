package com.gildongmu.dduru.domain.entity.matching

data class MatchingPostListEntity(
    val posts: List<MatchingPostEntity>,
    val nextCursor: Long?,
    val nextCursorValue: Int?,
    val hasNext: Boolean,
    val size: Int
)

data class MatchingPostEntity(
    val id: Long,
    val title: String,
    val status: String,
    val isFull: Boolean,
    val startDate: String,
    val endDate: String,
    val destination: String,
    val recruitCapacity: Int,
    val recruitCount: Int,
    val preferredGender: String,
    val companionType: String,
    val photoUrl: String?,
    val likeCount: Int,
    val hasLiked: Boolean,
    val author: MatchingPostAuthorEntity
)

data class MatchingPostAuthorEntity(
    val nickname: String,
    val isSuperHost: Boolean
)
