package com.gildongmu.dduru.presentation.feature.journey.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JourneyAdminUiState(
    val members: ImmutableList<JourneyAdminMember> = persistentListOf()
)

data class JourneyAdminMember(
    val id: Long,
    val username: String,
    val profileImageUrl: String? = null,
    val roles: ImmutableList<String> = persistentListOf()
)

val DEFAULT_JOURNEY_ROLES = persistentListOf(
    "총무",
    "일정 담당",
    "사진 담당",
    "길찾기 담당",
    "예약 담당"
)
