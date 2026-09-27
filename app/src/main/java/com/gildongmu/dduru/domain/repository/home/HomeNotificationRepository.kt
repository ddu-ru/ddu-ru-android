package com.gildongmu.dduru.domain.repository.home

import com.gildongmu.dduru.domain.entity.home.HomeNotification

interface HomeNotificationRepository {
    suspend fun getNotifications(): Result<List<HomeNotification>>
    suspend fun getUnreadCount(): Result<Int>
    suspend fun markAsRead(notificationId: Long): Result<Unit>
    suspend fun markAllAsRead(): Result<Unit>
    suspend fun updateSettings(enabled: Boolean): Result<Unit>
}
