package com.gildongmu.dduru.data.remote.dto.response.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyDetailResponse(
    val journeyId: Long,
    val groupRoomId: Long,
    val title: String,
    val photoUrl: String,
    val startDate: String,
    val endDate: String,
    val recruitCount: Int,
    val recruitDeadlineDDay: String,
    val recruitCapacity: Int,
    val isOwner: Boolean,
    val members: List<JourneyMemberResponse>,
    val pinnedNotices: List<JourneyPinnedNoticeResponse>
)

@Serializable
data class JourneyUpdateResponse(
    val journeyId: Long,
    val title: String,
    val photoUrl: String,
    val startDate: String,
    val endDate: String
)

@Serializable
data class JourneyMemberResponse(
    val userId: Long,
    val nickname: String,
    val profileImage: JourneyProfileImageResponse,
    val isHost: Boolean,
    val gender: String,
    val ageGroup: String,
    val roles: List<JourneyMemberRoleResponse>
)

@Serializable
data class JourneyMemberRoleResponse(
    val roleType: String,
    val customRoleLabel: String?
)

@Serializable
data class JourneyMemberRoleUpdateResponse(
    val memberUserId: Long,
    val roles: List<JourneyMemberRoleResponse>
)

@Serializable
data class JourneyPinnedNoticeResponse(
    val journeyPostId: Long,
    val content: String,
    val createdAt: String
)

@Serializable
data class MyJourneyListResponse(
    val activeJourneys: List<MyJourneyResponse>,
    val completedJourneys: List<MyJourneyResponse>
)

@Serializable
data class MyJourneyResponse(
    val journeyId: Long,
    val title: String,
    val photoUrl: String,
    val startDate: String,
    val endDate: String,
    val tripDurationText: String,
    val destination: String,
    val recruitCount: Int,
    val recruitCapacity: Int,
    val startDDay: String,
    val isOwner: Boolean
)
