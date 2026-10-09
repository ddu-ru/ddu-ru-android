package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.journey.toDomain
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyPostNoticeRequest
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyPostRequest
import com.gildongmu.dduru.data.remote.service.JourneyPostService
import com.gildongmu.dduru.domain.entity.journey.JourneyPost
import com.gildongmu.dduru.domain.entity.journey.JourneyPostCommand
import com.gildongmu.dduru.domain.entity.journey.JourneyPostNotice
import com.gildongmu.dduru.domain.entity.journey.JourneyPostPage
import com.gildongmu.dduru.domain.repository.JourneyPostRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class JourneyPostRepositoryImpl @Inject constructor(
    private val journeyPostService: JourneyPostService,
    private val json: Json
) : JourneyPostRepository {
    override suspend fun getJourneyPosts(
        journeyId: Long,
        cursor: Long?,
        size: Int?
    ): Result<JourneyPostPage> = request {
        val response = journeyPostService.getJourneyPosts(journeyId, cursor, size)
        response.requireData().toDomain()
    }

    override suspend fun createJourneyPost(
        journeyId: Long,
        command: JourneyPostCommand
    ): Result<JourneyPost> = request {
        val response = journeyPostService.createJourneyPost(
            journeyId = journeyId,
            request = JourneyPostRequest(content = command.content, imageUrls = command.imageUrls)
        )
        response.requireData().toDomain()
    }

    override suspend fun getJourneyPost(journeyId: Long, journeyPostId: Long): Result<JourneyPost> = request {
        journeyPostService.getJourneyPost(journeyId, journeyPostId).requireData().toDomain()
    }

    override suspend fun updateJourneyPost(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyPostCommand
    ): Result<JourneyPost> = request {
        val response = journeyPostService.updateJourneyPost(
            journeyId = journeyId,
            journeyPostId = journeyPostId,
            request = JourneyPostRequest(content = command.content, imageUrls = command.imageUrls)
        )
        response.requireData().toDomain()
    }

    override suspend fun deleteJourneyPost(journeyId: Long, journeyPostId: Long): Result<Unit> = request {
        val response = journeyPostService.deleteJourneyPost(journeyId, journeyPostId)
        if (response.code() != 204) throw response.toJourneyApiException(json)
    }

    override suspend fun updateJourneyPostNotice(
        journeyId: Long,
        journeyPostId: Long,
        isNotice: Boolean
    ): Result<JourneyPostNotice> = request {
        val response = journeyPostService.updateJourneyPostNotice(
            journeyId = journeyId,
            journeyPostId = journeyPostId,
            request = JourneyPostNoticeRequest(isNotice)
        )
        response.requireData().toDomain()
    }

    private suspend fun <T> request(block: suspend () -> T): Result<T> = try {
        Result.success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: HttpException) {
        Result.failure(exception.toJourneyApiException(json))
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    private fun <T> com.gildongmu.dduru.data.remote.dto.response.ApiResponse<T>.requireData(): T {
        if (status != 0 && status !in 200..299) throw toJourneyApiException()
        return data ?: throw IllegalStateException("Response data is null")
    }
}
