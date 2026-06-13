package com.gildongmu.dduru.presentation.feature.home.state

import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.home.model.AiMatchingCardData
import com.gildongmu.dduru.presentation.feature.home.model.HomePopularDestinationUiModel
import com.gildongmu.dduru.presentation.feature.home.model.TravelCompanionUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val upcomingTrip: UpcomingTripUiState = UpcomingTripUiState(),
    val popularDestinations: ImmutableList<HomePopularDestinationUiModel> = persistentListOf(),
    val popularDestinationUpdatedText: String = "방금 전",
    val aiMatchingUsedCount: Int = 0,
    val aiMatchingCard: AiMatchingCardData? = fakeAiMatchingCard,
    val superHosts: List<HomeSuperHostUiState> = emptyList(),
    val sameDestinationCompanions: List<TravelCompanionUiModel> = emptyList(),
    val sameAgeCompanions: List<TravelCompanionUiModel> = emptyList()
) {
    companion object {
        val fake = HomeUiState(
            upcomingTrip = UpcomingTripUiState(
                title = "제주도 힐링 여행",
                currentMember = 3,
                maxMember = 4,
                dDay = 12,
                taskCount = 1
            ),
            popularDestinations = persistentListOf(
                HomePopularDestinationUiModel(
                    id = 1L,
                    rank = 1,
                    destinationName = "제주도",
                    waitingMateCount = 14231,
                    whyHotText = "유채꽃이 활짝 폈어요!",
                    hashtags = persistentListOf("힐링", "드라이브", "바다"),
                    imageUrl = ""
                ),
                HomePopularDestinationUiModel(
                    id = 2L,
                    rank = 2,
                    destinationName = "도쿄",
                    waitingMateCount = 8231,
                    whyHotText = "봄 여행 문의가 많아요!",
                    hashtags = persistentListOf("맛집", "쇼핑", "도시"),
                    imageUrl = ""
                )
            ),
            aiMatchingUsedCount = 0,
            superHosts = listOf(
                HomeSuperHostUiState(
                    id = 1L,
                    title = "제주 동쪽 일출 투어",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    hashtags = listOf("일출", "등산"),
                    hostName = "username",
                    viewCount = 723,
                    thumbnailImageRes = R.drawable.fukuoka,
                    hostProfileImageRes = R.drawable.img_profile
                ),
                HomeSuperHostUiState(
                    id = 2L,
                    title = "제주 중턱 일출 투어",
                    location = "제주도 한라산",
                    dateRange = "03.15 - 03.18",
                    currentMemberCount = 3,
                    maxMemberCount = 4,
                    hashtags = listOf("일출", "등산"),
                    hostName = "username",
                    viewCount = 723,
                    thumbnailImageRes = R.drawable.fukuoka,
                    hostProfileImageRes = R.drawable.img_profile
                )
            ),
            sameDestinationCompanions = travelCompanionFakeItems,
            sameAgeCompanions = travelCompanionFakeItems
        )
    }
}

data class UpcomingTripUiState(
    val title: String = "",
    val currentMember: Int = 0,
    val maxMember: Int = 0,
    val dDay: Int = 0,
    val taskCount: Int = 0
)

data class HomeSuperHostUiState(
    val id: Long,
    val title: String,
    val location: String,
    val dateRange: String,
    val currentMemberCount: Int,
    val maxMemberCount: Int,
    val hashtags: List<String>,
    val hostName: String,
    val viewCount: Int,
    val thumbnailImageRes: Int? = null,
    val hostProfileImageRes: Int? = null,
    val recruitmentText: String = "모집중",
    val isLiked: Boolean = false
)

private val fakeAiMatchingCard = AiMatchingCardData(
    imageUrl = null,
    matchPercent = 92,
    title = "제주 서귀포 감성 여행",
    location = "제주 서귀포",
    dateRange = "2026.03.15 - 03.18",
    profileImageUrl = null,
    username = "username",
    age = 28,
    gender = "여성",
    currentMembers = 3,
    maxMembers = 4,
    description = "제주 서쪽 해안을 따라 느긋하게 드라이브하며 숨은 카페들을 찾아가는 여행입니다.",
    hashtags = listOf("카페투어", "사진")
)

private val travelCompanionFakeItems = listOf(
    TravelCompanionUiModel(
        id = 1L,
        title = "Title",
        location = "제주도 한라산",
        dateText = "03.15 - 03.18",
        currentMemberCount = 3,
        totalMemberCount = 4,
        imageRes = R.drawable.fukuoka
    ),
    TravelCompanionUiModel(
        id = 2L,
        title = "Title",
        location = "제주도 한라산",
        dateText = "03.15 - 03.18",
        currentMemberCount = 3,
        totalMemberCount = 4,
        imageRes = R.drawable.fukuoka
    ),
    TravelCompanionUiModel(
        id = 3L,
        title = "Title",
        location = "제주도 한라산",
        dateText = "03.15 - 03.18",
        currentMemberCount = 3,
        totalMemberCount = 4,
        imageRes = R.drawable.fukuoka
    )
)
