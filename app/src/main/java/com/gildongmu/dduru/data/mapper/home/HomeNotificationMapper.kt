package com.gildongmu.dduru.data.mapper.home

import com.gildongmu.dduru.data.remote.dto.response.home.HomeNotificationResponse
import com.gildongmu.dduru.domain.entity.home.HomeNotification
import com.gildongmu.dduru.domain.entity.home.HomeNotificationCategory
import com.gildongmu.dduru.domain.entity.home.HomeNotificationResourceType
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun HomeNotificationResponse.toDomain(): HomeNotification {
    return HomeNotification(
        id = notificationId,
        title = type.toNotificationTitle(),
        body = body,
        timeAgo = createdAt.toTimeAgo(),
        category = type.toNotificationCategory(),
        isRead = isRead,
        type = type,
        resourceType = resourceType.toNotificationResourceType(),
        resourceId = resourceId
    )
}

private fun String.toNotificationTitle(): String {
    return when (this) {
        "MATCH_APPLIED" -> "매칭 신청 도착"
        "MATCH_APPROVED" -> "매칭 승인"
        "JOURNEY_NOTICE_CREATED" -> "공지 등록"
        "SCHEDULE_CREATED" -> "일정 생성"
        "SCHEDULE_CHANGED" -> "일정 변경"
        "SCHEDULE_CANCELED" -> "일정 취소"
        "SCHEDULE_IMMINENT" -> "일정 임박"
        "POST_LIKED_UPDATED" -> "찜한 글 업데이트"
        else -> this
    }
}

private fun String.toNotificationCategory(): HomeNotificationCategory {
    return when (this) {
        "MATCH_APPLIED",
        "MATCH_APPROVED" -> HomeNotificationCategory.MATCHING
        "JOURNEY_NOTICE_CREATED" -> HomeNotificationCategory.MESSAGE
        "SCHEDULE_CREATED",
        "SCHEDULE_CHANGED",
        "SCHEDULE_CANCELED",
        "SCHEDULE_IMMINENT" -> HomeNotificationCategory.SCHEDULE
        "POST_LIKED_UPDATED" -> HomeNotificationCategory.RECRUITMENT
        else -> HomeNotificationCategory.SYSTEM
    }
}

private fun String.toNotificationResourceType(): HomeNotificationResourceType {
    return runCatching {
        HomeNotificationResourceType.valueOf(this)
    }.getOrDefault(HomeNotificationResourceType.UNKNOWN)
}

private fun String.toTimeAgo(): String {
    val createdAt = runCatching {
        LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }.getOrNull() ?: return this

    val duration = Duration.between(createdAt, LocalDateTime.now())
    val minutes = duration.toMinutes()
    val hours = duration.toHours()
    val days = duration.toDays()

    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        days == 1L -> "어제"
        else -> "${days}일 전"
    }
}
