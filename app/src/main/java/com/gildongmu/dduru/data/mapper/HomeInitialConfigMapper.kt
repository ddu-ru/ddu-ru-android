package com.gildongmu.dduru.data.mapper

import com.gildongmu.dduru.data.remote.dto.response.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.HomeSectionConfigResponse
import com.gildongmu.dduru.domain.entity.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.HomeSectionConfig

fun HomeInitialConfigResponse.toDomain(): HomeInitialConfig {
    return HomeInitialConfig(
        userAccessStatus = userAccessStatus,
        sections = sections.map { it.toDomain() }
    )
}

fun HomeSectionConfigResponse.toDomain(): HomeSectionConfig {
    return HomeSectionConfig(
        key = key,
        enabled = enabled,
        endpoint = endpoint,
        disabledReason = disabledReason
    )
}
