package com.gildongmu.dduru.data.remote.datasourceimpl

import com.gildongmu.dduru.data.remote.datasource.MatchingPostRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.service.MatchingPostService
import javax.inject.Inject
import kotlinx.serialization.json.JsonElement

class MatchingPostRemoteDataSourceImpl @Inject constructor(
    private val matchingPostService: MatchingPostService
) : MatchingPostRemoteDataSource {
    override suspend fun getPosts(
        cursor: Long?,
        cursorValue: Int?,
        size: Int?,
        keyword: String?,
        startDate: String?,
        endDate: String?,
        preferredGender: String?,
        minAge: Int?,
        maxAge: Int?,
        destinationId: Long?,
        recruitmentStatus: String?,
        companionType: String?,
        sort: String?
    ): ApiResponse<JsonElement> {
        return matchingPostService.getPosts(
            cursor = cursor,
            cursorValue = cursorValue,
            size = size,
            keyword = keyword,
            startDate = startDate,
            endDate = endDate,
            preferredGender = preferredGender,
            minAge = minAge,
            maxAge = maxAge,
            destinationId = destinationId,
            recruitmentStatus = recruitmentStatus,
            companionType = companionType,
            sort = sort
        )
    }
}
