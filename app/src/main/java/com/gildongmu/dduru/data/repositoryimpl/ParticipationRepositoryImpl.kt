package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse
import com.gildongmu.dduru.data.remote.service.ParticipationService
import com.gildongmu.dduru.domain.entity.MyParticipationEntity
import com.gildongmu.dduru.domain.entity.ParticipationException
import com.gildongmu.dduru.domain.entity.ParticipationRetrieveEntity
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException

class ParticipationRepositoryImpl @Inject constructor(
    private val participationService: ParticipationService,
    private val json: Json
) : ParticipationRepository {

    override suspend fun getParticipations(): Result<List<ParticipationRetrieveEntity>> {
        return try {
            val response = participationService.getParticipations()
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success((response.data ?: emptyList()).map { it.toEntity() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun contactParticipation(participationId: Long): Result<Long> {
        return try {
            val response = participationService.contactParticipation(participationId)
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            val data = response.data ?: return Result.failure(Exception("Empty response"))
            Result.success(data.privateRoomId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Result.failure(e.toParticipationException(json))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun approveParticipation(participationId: Long): Result<Long?> {
        return try {
            val response = participationService.approveParticipation(participationId)
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success(response.data?.groupRoomId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Result.failure(e.toParticipationException(json))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectParticipation(participationId: Long): Result<Unit> {
        return try {
            val response = participationService.rejectParticipation(participationId)
            if (!response.isSuccessful) return Result.failure(Exception("Status ${response.code()}"))
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Result.failure(e.toParticipationException(json))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyParticipations(): Result<List<MyParticipationEntity>> {
        return try {
            val response = participationService.getMyParticipations()
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success((response.data ?: emptyList()).map { it.toEntity() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelParticipation(participationId: Long): Result<Unit> {
        return try {
            val response = participationService.cancelParticipation(participationId)
            if (!response.isSuccessful) return Result.failure(Exception("Status ${response.code()}"))
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Result.failure(e.toParticipationException(json))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun ParticipationRetrieveResponse.toEntity() = ParticipationRetrieveEntity(
        participationId = participationId,
        userId = userId,
        userName = userName,
        profileImage = profileImage?.url,
        message = message,
        status = status,
        appliedAt = appliedAt,
        postId = postId,
        postTitle = postTitle
    )

    private fun MyParticipationResponse.toEntity() = MyParticipationEntity(
        participationId = participationId,
        postId = postId,
        postTitle = postTitle,
        status = status,
        appliedAt = appliedAt,
        cancellable = cancellable,
        privateRoomId = privateRoomId,
        groupRoomId = groupRoomId
    )

    private fun HttpException.toParticipationException(json: Json): ParticipationException {
        val body = response()?.errorBody()?.string()
        val data = body?.let {
            runCatching { json.parseToJsonElement(it).jsonObject["data"]?.jsonObject }.getOrNull()
        }
        return ParticipationException(
            errorCode = data?.get("errorCode")?.jsonPrimitive?.contentOrNull,
            message = data?.get("message")?.jsonPrimitive?.contentOrNull
        )
    }
}
