package com.gildongmu.dduru.data.mapper.journey

import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyDetailResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyMemberResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyMemberRoleResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyMemberRoleUpdateResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyPinnedNoticeResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.JourneyUpdateResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.MyJourneyListResponse
import com.gildongmu.dduru.data.remote.dto.response.journey.MyJourneyResponse
import com.gildongmu.dduru.domain.journey.entity.JourneyDetail
import com.gildongmu.dduru.domain.journey.entity.JourneyMember
import com.gildongmu.dduru.domain.journey.entity.JourneyMemberRole
import com.gildongmu.dduru.domain.journey.entity.JourneyMemberRoleUpdate
import com.gildongmu.dduru.domain.journey.entity.JourneyPinnedNotice
import com.gildongmu.dduru.domain.journey.entity.JourneyUpdate
import com.gildongmu.dduru.domain.journey.entity.MyJourney
import com.gildongmu.dduru.domain.journey.entity.MyJourneyList

fun JourneyDetailResponse.toDomain(): JourneyDetail = JourneyDetail(
    journeyId = journeyId,
    groupRoomId = groupRoomId,
    title = title,
    photoUrl = photoUrl,
    startDate = startDate,
    endDate = endDate,
    recruitCount = recruitCount,
    recruitDeadlineDDay = recruitDeadlineDDay,
    recruitCapacity = recruitCapacity,
    isOwner = isOwner,
    members = members.map { it.toDomain() },
    pinnedNotices = pinnedNotices.map { it.toDomain() }
)

fun JourneyUpdateResponse.toDomain(): JourneyUpdate = JourneyUpdate(
    journeyId = journeyId,
    title = title,
    photoUrl = photoUrl,
    startDate = startDate,
    endDate = endDate
)

fun JourneyMemberResponse.toDomain(): JourneyMember = JourneyMember(
    userId = userId,
    nickname = nickname,
    profileImage = profileImage.toDomain(),
    isHost = isHost,
    gender = gender,
    ageGroup = ageGroup,
    roles = roles.map { it.toDomain() }
)

fun JourneyMemberRoleResponse.toDomain(): JourneyMemberRole = JourneyMemberRole(
    roleType = roleType,
    customRoleLabel = customRoleLabel
)

fun JourneyMemberRoleUpdateResponse.toDomain(): JourneyMemberRoleUpdate = JourneyMemberRoleUpdate(
    memberUserId = memberUserId,
    roles = roles.map { it.toDomain() }
)

fun JourneyPinnedNoticeResponse.toDomain(): JourneyPinnedNotice = JourneyPinnedNotice(
    journeyPostId = journeyPostId,
    content = content,
    createdAt = createdAt
)

fun MyJourneyListResponse.toDomain(): MyJourneyList = MyJourneyList(
    activeJourneys = activeJourneys.map { it.toDomain() },
    completedJourneys = completedJourneys.map { it.toDomain() }
)

fun MyJourneyResponse.toDomain(): MyJourney = MyJourney(
    journeyId = journeyId,
    title = title,
    photoUrl = photoUrl,
    startDate = startDate,
    endDate = endDate,
    tripDurationText = tripDurationText,
    destination = destination,
    recruitCount = recruitCount,
    recruitCapacity = recruitCapacity,
    startDDay = startDDay,
    isOwner = isOwner
)
