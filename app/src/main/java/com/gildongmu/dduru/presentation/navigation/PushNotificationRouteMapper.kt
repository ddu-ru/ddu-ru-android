package com.gildongmu.dduru.presentation.navigation

import android.content.Intent
import com.gildongmu.dduru.core.notification.DduruPushNotificationContract

fun Intent.toPushNotificationRoute(): RootNavRoute? {
    val resourceType = getStringExtra(DduruPushNotificationContract.KEY_RESOURCE_TYPE)
        ?: return null
    val resourceId = getStringExtra(DduruPushNotificationContract.KEY_RESOURCE_ID)?.toLongOrNull()

    return when (resourceType) {
        RESOURCE_TYPE_MATCH -> RootNavRoute.MainTab(BottomNavRoute.Matching)
        RESOURCE_TYPE_JOURNEY,
        RESOURCE_TYPE_JOURNEY_POST,
        RESOURCE_TYPE_SCHEDULE -> RootNavRoute.MainTab(BottomNavRoute.MyPage)
        RESOURCE_TYPE_POST -> resourceId?.let { RootNavRoute.MatchingDetail(it) }
            ?: RootNavRoute.MainTab(BottomNavRoute.Matching)
        RESOURCE_TYPE_CHAT_ROOM -> resourceId?.let { RootNavRoute.ChatRoom(it) }
            ?: RootNavRoute.MainTab(BottomNavRoute.Chat)
        else -> RootNavRoute.HomeNotification
    }
}

private const val RESOURCE_TYPE_MATCH = "MATCH"
private const val RESOURCE_TYPE_JOURNEY = "JOURNEY"
private const val RESOURCE_TYPE_JOURNEY_POST = "JOURNEY_POST"
private const val RESOURCE_TYPE_SCHEDULE = "SCHEDULE"
private const val RESOURCE_TYPE_POST = "POST"
private const val RESOURCE_TYPE_CHAT_ROOM = "CHAT_ROOM"
