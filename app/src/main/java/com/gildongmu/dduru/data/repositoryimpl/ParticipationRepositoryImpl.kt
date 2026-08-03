package com.gildongmu.dduru.data.repositoryimpl

import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse
import com.gildongmu.dduru.data.remote.service.ParticipationService
import com.gildongmu.dduru.domain.repository.ParticipationRepository
import javax.inject.Inject

class ParticipationRepositoryImpl @Inject constructor(
    private val participationService: ParticipationService
) : ParticipationRepository {

    override suspend fun getParticipations(): Result<List<ParticipationRetrieveResponse>> {
        return try {
            val response = participationService.getParticipations()
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun approveParticipation(participationId: Long): Result<Unit> {
        return try {
            participationService.approveParticipation(participationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectParticipation(participationId: Long): Result<Unit> {
        return try {
            participationService.rejectParticipation(participationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyParticipations(): Result<List<MyParticipationResponse>> {
        return try {
            val response = participationService.getMyParticipations()
            if (response.status != 200) return Result.failure(Exception("Status ${response.status}"))
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelParticipation(participationId: Long): Result<Unit> {
        return try {
            participationService.cancelParticipation(participationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
