package com.gildongmu.dduru.domain.entity.home

data class HomeInitialConfig(
    val userAccessStatus: String,
    val sections: List<HomeSectionConfig>
)

data class HomeSectionConfig(
    val key: String,
    val enabled: Boolean,
    val endpoint: String,
    val disabledReason: String?
)
