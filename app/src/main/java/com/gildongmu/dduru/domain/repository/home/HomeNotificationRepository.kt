package com.gildongmu.dduru.domain.repository.home

import com.gildongmu.dduru.domain.entity.home.HomeNotificationPage
import com.gildongmu.dduru.domain.entity.home.HomeNotificationUnreadCount

interface HomeNotificationRepository {
    suspend fun getNotifications(filter: String, cursor: Long? = null): Result<HomeNotificationPage>
    suspend fun getUnreadCount(): Result<HomeNotificationUnreadCount>
    suspend fun markAsRead(notificationId: Long): Result<Unit>
    suspend fun markAllAsRead(): Result<Unit>
    suspend fun deleteAll(): Result<Unit>
    suspend fun updateSettings(enabled: Boolean): Result<Unit>
}
