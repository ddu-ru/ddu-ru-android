package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.matching.MatchingPostAuthorResponse
import com.gildongmu.dduru.data.remote.dto.response.matching.MatchingPostListResponse
import com.gildongmu.dduru.data.remote.dto.response.matching.MatchingPostResponse
import com.gildongmu.dduru.domain.entity.matching.MatchingPostAuthorEntity
import com.gildongmu.dduru.domain.entity.matching.MatchingPostEntity
import com.gildongmu.dduru.domain.entity.matching.MatchingPostListEntity

fun MatchingPostListResponse.toDomain(): MatchingPostListEntity {
    return MatchingPostListEntity(
        posts = posts.map { it.toDomain() },
        nextCursor = nextCursor,
        nextCursorValue = nextCursorValue,
        hasNext = hasNext,
        size = size
    )
}

private fun MatchingPostResponse.toDomain(): MatchingPostEntity {
    return MatchingPostEntity(
        id = id,
        title = title,
        status = status,
        isFull = isFull,
        startDate = startDate,
        endDate = endDate,
        destination = destination,
        recruitCapacity = recruitCapacity,
        recruitCount = recruitCount,
        preferredGender = preferredGender,
        companionType = companionType,
        photoUrl = photoUrl,
        likeCount = likeCount,
        hasLiked = hasLiked,
        author = author.toDomain()
    )
}

private fun MatchingPostAuthorResponse.toDomain(): MatchingPostAuthorEntity {
    return MatchingPostAuthorEntity(
        nickname = nickname,
        isSuperHost = isSuperHost
    )
}
