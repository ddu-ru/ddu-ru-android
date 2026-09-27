package com.gildongmu.dduru.domain.entity.home

data class HomeNotification(
    val id: Long,
    val title: String,
    val body: String,
    val timeAgo: String,
    val category: HomeNotificationCategory,
    val isRead: Boolean
)

enum class HomeNotificationCategory {
    MATCHING,
    SCHEDULE,
    MESSAGE
}
