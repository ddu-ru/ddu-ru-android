package com.gildongmu.dduru.data.remote.dto.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeNotificationResponse(
    @SerialName("notificationId")
    val notificationId: Long,
    @SerialName("title")
    val title: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("body")
    val body: String = "",
    @SerialName("resourceType")
    val resourceType: String = "",
    @SerialName("resourceId")
    val resourceId: Long? = null,
    @SerialName("isRead")
    val isRead: Boolean = false,
    @SerialName("createdAt")
    val createdAt: String = ""
)

@Serializable
data class HomeNotificationsResponse(
    @SerialName("notifications")
    val notifications: List<HomeNotificationResponse> = emptyList(),
    @SerialName("nextCursor")
    val nextCursor: Long? = null,
    @SerialName("hasNext")
    val hasNext: Boolean = false,
    @SerialName("size")
    val size: Int = 0
)

@Serializable
data class HomeNotificationReadResponse(
    @SerialName("success")
    val success: Boolean = false
)

@Serializable
data class HomeNotificationUnreadCountResponse(
    @SerialName("unreadCount")
    val unreadCount: Int = 0,
    @SerialName("matchUnreadCount")
    val matchUnreadCount: Int = 0,
    @SerialName("journeyUnreadCount")
    val journeyUnreadCount: Int = 0
)
