package com.gildongmu.dduru.data.remote.service.home

import com.gildongmu.dduru.data.remote.dto.request.HomeNotificationSettingsRequest
import com.gildongmu.dduru.data.remote.dto.response.ApiResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationReadResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationUnreadCountResponse
import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeNotificationService {
    @GET("api/v1/notifications")
    suspend fun getNotifications(
        @Query("filter") filter: String,
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): ApiResponse<HomeNotificationsResponse>

    @PATCH("api/v1/notifications/{notificationId}/read")
    suspend fun markAsRead(
        @Path("notificationId") notificationId: Long
    ): ApiResponse<HomeNotificationReadResponse>

    @PATCH("api/v1/notifications/read-all")
    suspend fun markAllAsRead(): ApiResponse<HomeNotificationReadResponse>

    @DELETE("api/v1/notifications")
    suspend fun deleteAll(): Response<Unit>

    @GET("api/v1/notifications/unread-count")
    suspend fun getUnreadCount(): ApiResponse<HomeNotificationUnreadCountResponse>

    @PATCH("api/v1/notifications/settings")
    suspend fun updateSettings(
        @Body request: HomeNotificationSettingsRequest
    ): Response<Unit>

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
