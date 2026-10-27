package com.gildongmu.dduru.domain.journey.entity

data class JourneyDetail(
    val journeyId: Long,
    val groupRoomId: Long,
    val title: String,
    val photoUrl: String?,
    val startDate: String,
    val endDate: String,
    val recruitCount: Int,
    val recruitDeadlineDDay: String,
    val recruitCapacity: Int,
    val isOwner: Boolean,
    val members: List<JourneyMember>,
    val pinnedNotices: List<JourneyPinnedNotice>
)

data class JourneyUpdate(
    val journeyId: Long,
    val title: String,
    val photoUrl: String,
    val startDate: String,
    val endDate: String
)

data class JourneyUpdateCommand(
    val title: String? = null,
    val photoUrl: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)

data class JourneyMember(
    val userId: Long,
    val nickname: String,
    val profileImage: JourneyProfileImage,
    val isHost: Boolean,
    val gender: String,
    val ageGroup: String?,
    val roles: List<JourneyMemberRole>
)

data class JourneyMemberRole(
    val roleType: String,
    val customRoleLabel: String?
)

data class JourneyMemberRoleUpdate(
    val memberUserId: Long,
    val roles: List<JourneyMemberRole>
)

data class JourneyPinnedNotice(
    val journeyPostId: Long,
    val content: String,
    val createdAt: String
)

data class MyJourneyList(
    val activeJourneys: List<MyJourney>,
    val completedJourneys: List<MyJourney>
)

data class MyJourney(
    val journeyId: Long,
    val title: String,
    val photoUrl: String?,
    val startDate: String,
    val endDate: String,
    val tripDurationText: String,
    val destination: String,
    val recruitCount: Int,
    val recruitCapacity: Int,
    val startDDay: String,
    val isOwner: Boolean
)
