package com.gildongmu.dduru.data.remote.dto.request.journey

import kotlinx.serialization.Serializable

@Serializable
data class JourneyMemberRoleUpdateRequest(
    val roles: List<JourneyMemberRoleRequest>
)

@Serializable
data class JourneyMemberRoleRequest(
    val roleType: String,
    val customRoleLabel: String?
)
