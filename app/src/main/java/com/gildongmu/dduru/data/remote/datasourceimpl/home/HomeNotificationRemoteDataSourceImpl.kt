package com.gildongmu.dduru.data.remote.datasourceimpl.home

import com.gildongmu.dduru.data.remote.datasource.home.HomeNotificationRemoteDataSource
import com.gildongmu.dduru.data.remote.dto.request.HomeNotificationSettingsRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationReadResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationUnreadCountResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationsResponse
import com.gildongmu.dduru.data.remote.service.home.HomeNotificationService
import javax.inject.Inject
import retrofit2.Response

class HomeNotificationRemoteDataSourceImpl @Inject constructor(
    private val homeNotificationService: HomeNotificationService
) : HomeNotificationRemoteDataSource {
    override suspend fun getNotifications(
        cursor: Long?,
        size: Int
    ): ApiResponse<HomeNotificationsResponse> {
        return homeNotificationService.getNotifications(
            cursor = cursor,
            size = size
        )
    }

    override suspend fun markAsRead(notificationId: Long): ApiResponse<HomeNotificationReadResponse> {
        return homeNotificationService.markAsRead(notificationId = notificationId)
    }

    override suspend fun markAllAsRead(): ApiResponse<HomeNotificationReadResponse> {
        return homeNotificationService.markAllAsRead()
    }

    override suspend fun getUnreadCount(): ApiResponse<HomeNotificationUnreadCountResponse> {
        return homeNotificationService.getUnreadCount()
    }

    override suspend fun updateSettings(enabled: Boolean): Response<Unit> {
        return homeNotificationService.updateSettings(
            request = HomeNotificationSettingsRequest(enabled = enabled)
        )
    }
}
