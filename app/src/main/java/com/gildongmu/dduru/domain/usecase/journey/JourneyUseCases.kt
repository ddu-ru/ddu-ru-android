package com.gildongmu.dduru.domain.usecase.journey

import com.gildongmu.dduru.domain.entity.journey.JourneyDetail
import com.gildongmu.dduru.domain.entity.journey.JourneyMember
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRole
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRoleUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdateCommand
import com.gildongmu.dduru.domain.entity.journey.MyJourneyList
import com.gildongmu.dduru.domain.repository.JourneyRepository
import javax.inject.Inject

class GetJourneyDetailUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(journeyId: Long): Result<JourneyDetail> = repository.getJourneyDetail(journeyId)
}

class UpdateJourneyUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(journeyId: Long, command: JourneyUpdateCommand): Result<JourneyUpdate> =
        repository.updateJourney(journeyId, command)
}

class GetJourneyMembersUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(journeyId: Long): Result<List<JourneyMember>> =
        repository.getJourneyMembers(journeyId)
}

class RemoveJourneyMemberUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(journeyId: Long, memberUserId: Long): Result<Unit> =
        repository.removeJourneyMember(journeyId, memberUserId)
}

class ReplaceJourneyMemberRolesUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(
        journeyId: Long,
        memberUserId: Long,
        roles: List<JourneyMemberRole>
    ): Result<JourneyMemberRoleUpdate> = repository.replaceJourneyMemberRoles(journeyId, memberUserId, roles)
}

class GetMyJourneysUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(): Result<MyJourneyList> = repository.getMyJourneys()
}
