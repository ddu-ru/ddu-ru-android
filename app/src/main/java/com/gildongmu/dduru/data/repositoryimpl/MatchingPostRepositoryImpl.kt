package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.toDomain
import com.gildongmu.dduru.data.remote.datasource.MatchingPostRemoteDataSource
import com.gildongmu.dduru.domain.entity.matching.MatchingPostListEntity
import com.gildongmu.dduru.domain.repository.MatchingPostRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

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
        return try {
            val response = matchingPostRemoteDataSource.getPosts(
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

            if (response.status != SUCCESS_STATUS) {
                return Result.failure(Exception("Status ${response.status}"))
            }

            val data = response.data ?: return Result.failure(Exception("Response data is null"))
            Result.success(data.toDomain())
        } catch (e: HttpException) {
            Result.failure(Exception("서버 오류: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Result.failure(Exception("네트워크 오류: 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private companion object {
        const val SUCCESS_STATUS = 0
    }
}
