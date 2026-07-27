package com.gildongmu.dduru.presentation.feature.home.mapper

import com.gildongmu.dduru.R
import com.gildongmu.dduru.domain.entity.HomeMateRecommendation
import com.gildongmu.dduru.domain.entity.HomePopularDestination
import com.gildongmu.dduru.domain.entity.HomeSameAgeTrip
import com.gildongmu.dduru.domain.entity.HomeSameDestinationTrip
import com.gildongmu.dduru.domain.entity.HomeSuperHost
import com.gildongmu.dduru.domain.entity.HomeUpcomingTrip
import com.gildongmu.dduru.presentation.feature.home.model.AiMatchingCardData
import com.gildongmu.dduru.presentation.feature.home.model.HomePopularDestinationUiModel
import com.gildongmu.dduru.presentation.feature.home.model.HomeSuperHostCardUiModel
import com.gildongmu.dduru.presentation.feature.home.model.TravelCompanionUiModel
import com.gildongmu.dduru.presentation.feature.home.state.UpcomingTripUiState
import kotlinx.collections.immutable.toImmutableList

fun HomeMateRecommendation.toAiMatchingCardData(): AiMatchingCardData {
    return AiMatchingCardData(
        imageUrl = null,
        matchPercent = matchPercentage,
        title = title,
        location = location,
        dateRange = formatDateRange(startDate, endDate),
        profileImageUrl = host.profileImageInfo?.url,
        username = host.nickname,
        age = host.age,
        gender = host.gender.toGenderLabel(),
        currentMembers = currentMemberCount,
        maxMembers = maxMemberCount,
        description = description,
        hashtags = tags
    )
}

fun HomePopularDestination.toUiModel(): HomePopularDestinationUiModel {
    return HomePopularDestinationUiModel(
        id = destinationId,
        rank = rank,
        destinationName = destinationName,
        waitingMateCount = availableTripCount,
        whyHotText = DEFAULT_POPULAR_REASON,
        hashtags = tags.toImmutableList(),
        imageUrl = imageUrl
    )
}

fun HomeSameDestinationTrip.toTravelCompanionUiModel(): TravelCompanionUiModel {
    return TravelCompanionUiModel(
        id = postId,
        title = title,
        location = location,
        dateText = formatDateRange(startDate, endDate),
        currentMemberCount = currentMemberCount,
        totalMemberCount = maxMemberCount,
        imageRes = R.drawable.fukuoka,
        imageUrl = thumbnailUrl
    )
}

fun HomeSameAgeTrip.toTravelCompanionUiModel(): TravelCompanionUiModel {
    return TravelCompanionUiModel(
        id = postId,
        title = title,
        location = location,
        dateText = startDate.toDateLabel(includeYear = true),
        currentMemberCount = currentMemberCount,
        totalMemberCount = maxMemberCount,
        imageRes = R.drawable.fukuoka,
        imageUrl = thumbnailUrl
    )
}

fun HomeSuperHost.toUiModel(): HomeSuperHostCardUiModel {
    return HomeSuperHostCardUiModel(
        id = postId,
        title = title,
        location = location,
        dateRange = formatDateRange(startDate, endDate),
        currentMemberCount = currentMemberCount,
        maxMemberCount = maxMemberCount,
        hashtags = tags,
        hostName = host.nickname,
        viewCount = viewCount,
        thumbnailImageRes = R.drawable.fukuoka,
        thumbnailImageUrl = thumbnailUrl,
        hostProfileImageRes = R.drawable.img_profile,
        hostProfileImageUrl = host.profileImageInfo?.url,
        recruitmentText = status.toRecruitmentText(),
        isLiked = hasLiked
    )
}

fun HomeUpcomingTrip.toUiState(): UpcomingTripUiState {
    return UpcomingTripUiState(
        journeyId = journeyId,
        title = title,
        dateText = formatDateRange(startDate, endDate),
        currentMember = currentMemberCount,
        maxMember = maxMemberCount,
        dDay = dDay,
        taskCount = pendingTaskCount
    )
}

private fun formatDateRange(startDate: String, endDate: String): String {
    val start = startDate.toDateLabel(includeYear = true)
    val end = endDate.toDateLabel(includeYear = false)

    return when {
        start.isBlank() && end.isBlank() -> ""
        end.isBlank() -> start
        start.isBlank() -> end
        else -> "$start - $end"
    }
}

private fun String.toRecruitmentText(): String {
    return when (this) {
        "OPEN" -> "모집중"
        "CLOSED" -> "모집완료"
        else -> this
    }
}

private fun String.toGenderLabel(): String {
    return when (this) {
        "M" -> "남성"
        "F" -> "여성"
        else -> this
    }
}

private const val DEFAULT_POPULAR_REASON = "지금 모집 중인 여행이 있어요!"
