package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.data.remote.dto.response.participation.MyParticipationResponse
import com.gildongmu.dduru.data.remote.dto.response.participation.ParticipationRetrieveResponse

interface ParticipationRepository {
    suspend fun getParticipations(): Result<List<ParticipationRetrieveResponse>>
    suspend fun approveParticipation(participationId: Long): Result<Unit>
    suspend fun rejectParticipation(participationId: Long): Result<Unit>
    suspend fun getMyParticipations(): Result<List<MyParticipationResponse>>
    suspend fun cancelParticipation(participationId: Long): Result<Unit>
}
