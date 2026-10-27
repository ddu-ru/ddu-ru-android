package com.gildongmu.dduru.presentation.feature.home.state

import androidx.compose.runtime.Immutable
import com.gildongmu.dduru.domain.entity.home.HomeNotificationResourceType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeNotificationItemUiState(
    val id: Long,
    val title: String,
    val body: String,
    val timeAgo: String,
    val isRead: Boolean,
    val type: String,
    val resourceType: HomeNotificationResourceType,
    val resourceId: Long?
)

@Immutable
data class HomeNotificationUiState(
    val selectedFilter: HomeNotificationFilter = HomeNotificationFilter.ALL,
    val unreadCount: Int = 0,
    val matchUnreadCount: Int = 0,
    val journeyUnreadCount: Int = 0,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isMarkingAllAsRead: Boolean = false,
    val isDeletingAll: Boolean = false,
    val markAllAsReadFailed: Boolean = false,
    val deleteAllFailed: Boolean = false,
    val notifications: ImmutableList<HomeNotificationItemUiState> = persistentListOf()
) {
    companion object {
        val preview = HomeNotificationUiState(
            unreadCount = 3,
            notifications = persistentListOf(
                HomeNotificationItemUiState(
                    id = 1,
                    title = "일정이 변경되었어요",
                    body = "제주 서쪽 일출 투어",
                    timeAgo = "5분 전",
                    isRead = false,
                    type = "SCHEDULE_UPDATED",
                    resourceType = HomeNotificationResourceType.SCHEDULE,
                    resourceId = 91
                ),
                HomeNotificationItemUiState(
                    id = 2,
                    title = "새로운 매칭 신청이 왔어요",
                    body = "제주 동쪽 일출 투어",
                    timeAgo = "12분 전",
                    isRead = false,
                    type = "MATCH_APPLIED",
                    resourceType = HomeNotificationResourceType.MATCH,
                    resourceId = 10
                )
            )
        )
    }
}

enum class HomeNotificationFilter(
    val label: String,
    val apiValue: String
) {
    ALL("전체", "ALL"),
    UNREAD("읽지 않음", "UNREAD"),
    MATCH("모집·매칭", "MATCH"),
    JOURNEY("여정", "JOURNEY")
}
