package com.gildongmu.dduru.presentation.navigation

import android.content.Intent
import com.gildongmu.dduru.core.notification.DduruPushNotificationContract
import com.gildongmu.dduru.domain.entity.home.HomeNotificationResourceType

fun Intent.toPushNotificationRoute(): RootNavRoute? {
    val resourceType = getStringExtra(DduruPushNotificationContract.KEY_RESOURCE_TYPE)
        ?.toHomeNotificationResourceType()
        ?: return null
    val resourceId = getStringExtra(DduruPushNotificationContract.KEY_RESOURCE_ID)?.toLongOrNull()

    return resourceType.toPushNotificationRoute(resourceId) ?: RootNavRoute.HomeNotification
}

fun HomeNotificationResourceType.toPushNotificationRoute(resourceId: Long?): RootNavRoute? {
    return when (this) {
        HomeNotificationResourceType.MATCH -> RootNavRoute.MainTab(BottomNavRoute.Matching)
        HomeNotificationResourceType.JOURNEY,
        HomeNotificationResourceType.JOURNEY_POST,
        HomeNotificationResourceType.SCHEDULE -> RootNavRoute.MainTab(BottomNavRoute.Journey)
        HomeNotificationResourceType.POST -> resourceId?.let { RootNavRoute.MatchingDetail(it) }
            ?: RootNavRoute.MainTab(BottomNavRoute.Matching)
        HomeNotificationResourceType.CHAT_ROOM -> resourceId?.let { RootNavRoute.ChatRoom(it) }
            ?: RootNavRoute.MainTab(BottomNavRoute.Chat)
        HomeNotificationResourceType.UNKNOWN -> null
    }
}

private fun String.toHomeNotificationResourceType(): HomeNotificationResourceType {
    return HomeNotificationResourceType.entries
        .firstOrNull { it.name == this }
        ?: HomeNotificationResourceType.UNKNOWN
}
