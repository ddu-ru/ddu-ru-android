package com.gildongmu.dduru.presentation.feature.journey.state

data class JourneyAdminUiState(
    val members: List<JourneyAdminMember> = emptyList(),
)

data class JourneyAdminMember(
    val id: Long,
    val username: String,
    val profileImageUrl: String? = null,
    val roles: List<JourneyMemberRole> = emptyList(),
)

enum class JourneyMemberRole(
    val label: String,
) {
    HOST("호스트"),
    GENERAL_AFFAIRS("총무"),
}
