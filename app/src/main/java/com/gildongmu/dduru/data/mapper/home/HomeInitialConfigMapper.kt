package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomeInitialConfigResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeSectionConfigResponse
import com.gildongmu.dduru.domain.entity.home.HomeInitialConfig
import com.gildongmu.dduru.domain.entity.home.HomeSectionConfig

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
