package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.toDomain
import com.gildongmu.dduru.data.remote.datasource.MatchingPostRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.response.matching.MatchingPostListResponse
import com.gildongmu.dduru.data.remote.util.requestApi
import com.gildongmu.dduru.domain.entity.matching.MatchingPostListEntity
import com.gildongmu.dduru.domain.repository.MatchingPostRepository
import javax.inject.Inject

class MatchingPostRepositoryImpl @Inject constructor(
    private val matchingPostRemoteDataSource: MatchingPostRemoteDataSource
) : MatchingPostRepository {
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
    ): Result<MatchingPostListEntity> {
        return requestApi<MatchingPostListResponse, MatchingPostListEntity>(
            request = {
                matchingPostRemoteDataSource.getPosts(
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
            },
            mapper = { it.toDomain() }
        )
    }
}
