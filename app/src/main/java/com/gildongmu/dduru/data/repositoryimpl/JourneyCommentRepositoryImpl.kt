package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.mapper.journey.toDomain
import com.gildongmu.dduru.data.remote.dto.request.journey.JourneyCommentRequest
import com.gildongmu.dduru.data.remote.service.JourneyCommentService
import com.gildongmu.dduru.domain.entity.journey.JourneyComment
import com.gildongmu.dduru.domain.entity.journey.JourneyCommentCommand
import com.gildongmu.dduru.domain.entity.journey.JourneyCommentList
import com.gildongmu.dduru.domain.repository.JourneyCommentRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class JourneyCommentRepositoryImpl @Inject constructor(
    private val journeyCommentService: JourneyCommentService,
    private val json: Json
) : JourneyCommentRepository {
    override suspend fun getJourneyComments(
        journeyId: Long,
        journeyPostId: Long
    ): Result<JourneyCommentList> = request {
        journeyCommentService.getJourneyComments(journeyId, journeyPostId).requireData().toDomain()
    }

    override suspend fun createJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment> = request {
        val response = journeyCommentService.createJourneyComment(
            journeyId = journeyId,
            journeyPostId = journeyPostId,
            request = JourneyCommentRequest(command.content)
        )
        response.requireData().toDomain()
    }

    override suspend fun updateJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        commentId: Long,
        command: JourneyCommentCommand
    ): Result<JourneyComment> = request {
        val response = journeyCommentService.updateJourneyComment(
            journeyId = journeyId,
            journeyPostId = journeyPostId,
            commentId = commentId,
            request = JourneyCommentRequest(command.content)
        )
        response.requireData().toDomain()
    }

    override suspend fun deleteJourneyComment(
        journeyId: Long,
        journeyPostId: Long,
        commentId: Long
    ): Result<Unit> = request {
        val response = journeyCommentService.deleteJourneyComment(journeyId, journeyPostId, commentId)
        if (response.code() != 204) throw response.toJourneyApiException(json)
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
