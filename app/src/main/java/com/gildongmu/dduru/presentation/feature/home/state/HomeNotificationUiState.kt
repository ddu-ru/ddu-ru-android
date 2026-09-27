package com.gildongmu.dduru.presentation.feature.home.state

import androidx.compose.runtime.Immutable
import com.gildongmu.dduru.domain.entity.home.HomeNotificationCategory
import com.gildongmu.dduru.domain.entity.home.HomeNotificationResourceType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class HomeNotificationItemUiState(
    val id: Long,
    val title: String,
    val body: String,
    val timeAgo: String,
    val category: HomeNotificationCategory,
    val isRead: Boolean,
    val type: String,
    val resourceType: HomeNotificationResourceType,
    val resourceId: Long?
)

@Immutable
data class HomeNotificationUiState(
    val selectedFilter: HomeNotificationFilter = HomeNotificationFilter.ALL,
    val unreadCount: Int = 0,
    val notifications: ImmutableList<HomeNotificationItemUiState> = persistentListOf()
) {
    val filteredNotifications: ImmutableList<HomeNotificationItemUiState>
        get() = when (selectedFilter) {
            HomeNotificationFilter.ALL -> notifications
            HomeNotificationFilter.UNREAD ->
                notifications
                    .filter { !it.isRead }
                    .toImmutableList()
            HomeNotificationFilter.MATCHING ->
                notifications
                    .filter { it.category == HomeNotificationCategory.MATCHING }
                    .toImmutableList()
            HomeNotificationFilter.SCHEDULE ->
                notifications
                    .filter { it.category == HomeNotificationCategory.SCHEDULE }
                    .toImmutableList()
            HomeNotificationFilter.MESSAGE ->
                notifications
                    .filter { it.category == HomeNotificationCategory.MESSAGE }
                    .toImmutableList()
        }

    companion object {
        val preview = HomeNotificationUiState(
            unreadCount = 3,
            notifications = persistentListOf(
                HomeNotificationItemUiState(
                    id = 1,
                    title = "일정이 변경되었어요",
                    body = "제주 서쪽 일출 투어",
                    timeAgo = "5분 전",
                    category = HomeNotificationCategory.SCHEDULE,
                    isRead = false,
                    type = "SCHEDULE_CHANGED",
                    resourceType = HomeNotificationResourceType.SCHEDULE,
                    resourceId = 91
                ),
                HomeNotificationItemUiState(
                    id = 2,
                    title = "새로운 매칭 신청이 왔어요",
                    body = "제주 동쪽 일출 투어",
                    timeAgo = "12분 전",
                    category = HomeNotificationCategory.MATCHING,
                    isRead = false,
                    type = "MATCH_APPLIED",
                    resourceType = HomeNotificationResourceType.MATCH,
                    resourceId = 10
                )
            )
        )
    }
}

enum class HomeNotificationFilter(val label: String) {
    ALL("전체"),
    UNREAD("읽지 않음"),
    MATCHING("매칭"),
    SCHEDULE("일정"),
    MESSAGE("메시지")
}
