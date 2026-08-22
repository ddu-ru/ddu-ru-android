package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.matching.MatchingPostListEntity

interface MatchingPostRepository {
    suspend fun getPosts(
        cursor: Long? = null,
        cursorValue: Int? = null,
        size: Int? = null,
        keyword: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        preferredGender: String? = null,
        minAge: Int? = null,
        maxAge: Int? = null,
        destinationId: Long? = null,
        recruitmentStatus: String? = null,
        companionType: String? = null,
        sort: String? = null
    ): Result<MatchingPostListEntity>
}
