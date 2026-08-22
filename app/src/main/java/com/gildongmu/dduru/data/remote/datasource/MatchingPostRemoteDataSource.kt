package com.gildongmu.dduru.data.remote.datasource

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.matching.MatchingPostListResponse

interface MatchingPostRemoteDataSource {
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
    ): ApiResponse<MatchingPostListResponse>
}
