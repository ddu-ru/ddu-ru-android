package com.gildongmu.dduru.domain.repository.home

import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.entity.home.HomeNotificationPage

interface HomeNotificationRepository {
    suspend fun getNotifications(cursor: Long? = null): Result<HomeNotificationPage>
    suspend fun getUnreadCount(): Result<Int>
    suspend fun markAsRead(notificationId: Long): Result<Unit>
    suspend fun markAllAsRead(): Result<Unit>
    suspend fun updateSettings(enabled: Boolean): Result<Unit>
}
