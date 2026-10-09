package com.gildongmu.dduru.domain.repository

import com.gildongmu.dduru.domain.entity.journey.JourneyDetail
import com.gildongmu.dduru.domain.entity.journey.JourneyMember
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRole
import com.gildongmu.dduru.domain.entity.journey.JourneyMemberRoleUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdate
import com.gildongmu.dduru.domain.entity.journey.JourneyUpdateCommand
import com.gildongmu.dduru.domain.entity.journey.MyJourneyList

interface JourneyRepository {
    suspend fun getJourneyDetail(journeyId: Long): Result<JourneyDetail>
    suspend fun updateJourney(journeyId: Long, command: JourneyUpdateCommand): Result<JourneyUpdate>
    suspend fun getJourneyMembers(journeyId: Long): Result<List<JourneyMember>>
    suspend fun removeJourneyMember(journeyId: Long, memberUserId: Long): Result<Unit>
    suspend fun replaceJourneyMemberRoles(
        journeyId: Long,
        memberUserId: Long,
        roles: List<JourneyMemberRole>
    ): Result<JourneyMemberRoleUpdate>

    suspend fun getMyJourneys(): Result<MyJourneyList>
}
