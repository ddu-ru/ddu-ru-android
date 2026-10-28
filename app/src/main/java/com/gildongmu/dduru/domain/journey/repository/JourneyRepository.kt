package com.gildongmu.dduru.domain.journey.repository

import com.gildongmu.dduru.domain.journey.entity.JourneyDetail
import com.gildongmu.dduru.domain.journey.entity.JourneyMember
import com.gildongmu.dduru.domain.journey.entity.JourneyMemberRole
import com.gildongmu.dduru.domain.journey.entity.JourneyMemberRoleUpdate
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleCommand
import com.gildongmu.dduru.domain.journey.entity.JourneyScheduleList
import com.gildongmu.dduru.domain.journey.entity.JourneyUpdate
import com.gildongmu.dduru.domain.journey.entity.JourneyUpdateCommand
import com.gildongmu.dduru.domain.journey.entity.MyJourneyList

interface JourneyRepository {
    suspend fun getJourneyDetail(journeyId: Long): Result<JourneyDetail>
    suspend fun updateJourney(journeyId: Long, command: JourneyUpdateCommand): Result<JourneyUpdate>
    suspend fun getJourneySchedules(journeyId: Long): Result<JourneyScheduleList>
    suspend fun createJourneySchedule(journeyId: Long, command: JourneyScheduleCommand): Result<Unit>
    suspend fun getJourneyMembers(journeyId: Long): Result<List<JourneyMember>>
    suspend fun removeJourneyMember(journeyId: Long, memberUserId: Long): Result<Unit>
    suspend fun replaceJourneyMemberRoles(
        journeyId: Long,
        memberUserId: Long,
        roles: List<JourneyMemberRole>
    ): Result<JourneyMemberRoleUpdate>

    suspend fun getMyJourneys(): Result<MyJourneyList>
}
