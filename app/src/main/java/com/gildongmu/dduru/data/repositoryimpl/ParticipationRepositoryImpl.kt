package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse
import com.gildongmu.dduru.data.remote.service.ParticipationService
import com.gildongmu.dduru.domain.entity.MyParticipationEntity
import com.gildongmu.dduru.domain.entity.ParticipationRetrieveEntity
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import javax.inject.Inject

class ParticipationRepositoryImpl @Inject constructor(
    private val participationService: ParticipationService
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
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun approveParticipation(participationId: Long): Result<Long?> {
        return try {
            val response = participationService.approveParticipation(participationId)
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success(response.data?.groupRoomId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectParticipation(participationId: Long): Result<Unit> {
        return try {
            val response = participationService.rejectParticipation(participationId)
            if (!response.isSuccessful) return Result.failure(Exception("Status ${response.code()}"))
            Result.success(Unit)
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
}
