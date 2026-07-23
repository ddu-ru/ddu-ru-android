package com.gildongmu.dduru.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeInitialConfigResponse(
    val userAccessStatus: String = "",
    val sections: List<HomeSectionConfigResponse> = emptyList()
)

@Serializable
data class HomeSectionConfigResponse(
    val key: String = "",
    val enabled: Boolean = false,
    val endpoint: String = "",
    val disabledReason: String? = null
)
