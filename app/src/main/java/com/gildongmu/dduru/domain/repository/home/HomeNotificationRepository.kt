package com.gildongmu.dduru.domain.repository.home

import com.gildongmu.dduru.domain.entity.home.HomeNotification

interface HomeNotificationRepository {
    suspend fun getNotifications(): Result<List<HomeNotification>>
    suspend fun getUnreadCount(): Result<Int>
    suspend fun markAllAsRead(): Result<Unit>
    suspend fun deleteAll(): Result<Unit>
}
