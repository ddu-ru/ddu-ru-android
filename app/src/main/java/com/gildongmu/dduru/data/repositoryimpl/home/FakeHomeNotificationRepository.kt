package com.gildongmu.dduru.data.repositoryimpl.home

import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.entity.home.HomeNotificationCategory
import com.gildongmu.dduru.domain.repository.home.HomeNotificationRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

class FakeHomeNotificationRepository @Inject constructor() : HomeNotificationRepository {
    private var notifications = fakeNotifications

    override suspend fun getNotifications(): Result<List<HomeNotification>> {
        delay(NETWORK_DELAY_MILLIS)
        return Result.success(notifications)
    }

    override suspend fun getUnreadCount(): Result<Int> {
        delay(NETWORK_DELAY_MILLIS)
        return Result.success(notifications.count { !it.isRead })
    }

    override suspend fun markAllAsRead(): Result<Unit> {
        delay(NETWORK_DELAY_MILLIS)
        notifications = notifications.map { it.copy(isRead = true) }
        return Result.success(Unit)
    }

    override suspend fun deleteAll(): Result<Unit> {
        delay(NETWORK_DELAY_MILLIS)
        notifications = emptyList()
        return Result.success(Unit)
    }

    private companion object {
        const val NETWORK_DELAY_MILLIS = 250L

        val fakeNotifications = listOf(
            HomeNotification(
                id = 1,
                title = "일정이 변경되었어요",
                body = "제주 서쪽 일출 투어",
                timeAgo = "5분 전",
                category = HomeNotificationCategory.SCHEDULE,
                isRead = false
            ),
            HomeNotification(
                id = 2,
                title = "새로운 매칭 신청이 왔어요",
                body = "제주 동쪽 일출 투어",
                timeAgo = "12분 전",
                category = HomeNotificationCategory.MATCHING,
                isRead = false
            ),
            HomeNotification(
                id = 3,
                title = "메시지가 도착했어요",
                body = "후쿠오카 맛집 투어",
                timeAgo = "20분 전",
                category = HomeNotificationCategory.MESSAGE,
                isRead = false
            ),
            HomeNotification(
                id = 4,
                title = "동행이 확정되었어요",
                body = "오사카 감성 여행",
                timeAgo = "1시간 전",
                category = HomeNotificationCategory.MATCHING,
                isRead = true
            ),
            HomeNotification(
                id = 5,
                title = "메시지가 도착했어요",
                body = "삿포로 눈꽃 여행",
                timeAgo = "3시간 전",
                category = HomeNotificationCategory.MESSAGE,
                isRead = true
            ),
            HomeNotification(
                id = 6,
                title = "메시지가 도착했어요",
                body = "도쿄 야경 산책",
                timeAgo = "어제",
                category = HomeNotificationCategory.MESSAGE,
                isRead = true
            ),
            HomeNotification(
                id = 7,
                title = "메시지가 도착했어요",
                body = "강릉 바다 드라이브",
                timeAgo = "2일 전",
                category = HomeNotificationCategory.MESSAGE,
                isRead = true
            )
        )
    }
}
