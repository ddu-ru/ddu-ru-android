package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.MyParticipationEntity
import com.gildongmu.dduru.domain.entity.ParticipationRetrieveEntity

interface ParticipationRepository {
    suspend fun getParticipations(): Result<List<ParticipationRetrieveEntity>>
    suspend fun contactParticipation(participationId: Long): Result<Long>
    suspend fun approveParticipation(participationId: Long): Result<Long?>
    suspend fun rejectParticipation(participationId: Long): Result<Unit>
    suspend fun getMyParticipations(): Result<List<MyParticipationEntity>>
    suspend fun cancelParticipation(participationId: Long): Result<Unit>
}
