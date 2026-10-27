package com.gildongmu.dduru.data.remote.datasource.home

import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationReadResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationUnreadCountResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationsResponse
import retrofit2.Response

interface HomeNotificationRemoteDataSource {
    suspend fun getNotifications(
        filter: String,
        cursor: Long?,
        size: Int
    ): ApiResponse<HomeNotificationsResponse>

    suspend fun markAsRead(notificationId: Long): ApiResponse<HomeNotificationReadResponse>

    suspend fun markAllAsRead(): ApiResponse<HomeNotificationReadResponse>

    suspend fun deleteAll(): Response<Unit>

    suspend fun getUnreadCount(): ApiResponse<HomeNotificationUnreadCountResponse>

    suspend fun updateSettings(enabled: Boolean): Response<Unit>
}
