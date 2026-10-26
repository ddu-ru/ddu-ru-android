package com.gildongmu.dduru.domain.entity.home

data class HomeNotification(
    val id: Long,
    val title: String,
    val body: String,
    val timeAgo: String,
    val category: HomeNotificationCategory,
    val isRead: Boolean,
    val type: String,
    val resourceType: HomeNotificationResourceType,
    val resourceId: Long?
)

data class HomeNotificationPage(
    val notifications: List<HomeNotification>,
    val nextCursor: Long?,
    val hasNext: Boolean
)

enum class HomeNotificationCategory {
    MATCHING,
    MESSAGE,
    SCHEDULE,
    RECRUITMENT,
    SYSTEM
}

enum class HomeNotificationResourceType {
    MATCH,
    JOURNEY,
    JOURNEY_POST,
    SCHEDULE,
    POST,
    CHAT_ROOM,
    UNKNOWN
}
