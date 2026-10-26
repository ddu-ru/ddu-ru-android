package com.gildongmu.dduru.presentation.feature.home.effect

import com.gildongmu.dduru.domain.entity.home.HomeNotificationResourceType

sealed interface HomeNotificationSideEffect {
    data class NavigateToResource(
        val resourceType: HomeNotificationResourceType,
        val resourceId: Long?
    ) : HomeNotificationSideEffect
}
