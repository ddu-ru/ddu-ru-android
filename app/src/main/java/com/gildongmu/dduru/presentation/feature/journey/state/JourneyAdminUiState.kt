package com.gildongmu.dduru.presentation.feature.journey.state

data class JourneyAdminUiState(
    val members: List<JourneyAdminMember> = emptyList()
)

data class JourneyAdminMember(
    val id: Long,
    val username: String,
    val profileImageUrl: String? = null,
    val roles: List<String> = emptyList()
)

val DEFAULT_JOURNEY_ROLES = listOf(
    "총무",
    "일정 담당",
    "사진 담당",
    "길찾기 담당",
    "예약 담당"
)
