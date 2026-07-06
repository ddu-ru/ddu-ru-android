package com.gildongmu.dduru.presentation.feature.matching.post.uistate

import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingPostParticipationStatus
import com.gildongmu.dduru.presentation.feature.matching.post.component.MatchingPostRecruitStatus

data class MatchingDetailUiState(
    val id: Long = 101,
    val title: String = "제주도 2박 3일 동행 구해요",
    val content: String = "제주도 서쪽 위주로 천천히 여행하실 분을 찾습니다. 카페와 맛집도 들르고, 사진도 여유롭게 찍는 일정으로 생각하고 있어요. 일정이 비슷하신 분이면 편하게 같이 다녀요.",
    val status: MatchingPostRecruitStatus = MatchingPostRecruitStatus.OPEN,
    val isFull: Boolean = false,
    val daysUntilRecruitDeadline: Int = 3,
    val daysUntilTravelStart: Int = 7,
    val startDate: String = "2026-07-10",
    val endDate: String = "2026-07-12",
    val destination: String = "제주",
    val recruitCapacity: Int = 4,
    val recruitCount: Int = 2,
    val recruitDeadline: String = "2026-07-09",
    val preferredGender: String = "U",
    val isAgeAny: Boolean = false,
    val minAge: Int = 20,
    val maxAge: Int = 29,
    val photoUrl: String? = null,
    val tags: List<String> = listOf("맛집", "힐링", "드라이브", "사진"),
    val viewCount: Int = 128,
    val likeCount: Int = 12,
    val createdAt: String = "2026-06-29T12:34:56",
    val author: MatchingDetailAuthorUiState = MatchingDetailAuthorUiState(),
    val isOwner: Boolean = false,
    val canEditPost: Boolean = false,
    val hasLiked: Boolean = true,
    val participants: List<MatchingDetailParticipantUiState> = listOf(
        MatchingDetailParticipantUiState(isHost = true),
        MatchingDetailParticipantUiState(
            userId = 44,
            nickname = "제주러버",
            gender = "M",
            ageGroup = 30
        )
    ),
    val myParticipationStatus: MatchingPostParticipationStatus = MatchingPostParticipationStatus.PENDING,
    val tripDurationText: String = "2박 3일",
    val recruitDeadlineDDay: String = "D-3",
    val companionType: String = "FULL"
) {
    val preferredGenderText: String
        get() = when (preferredGender) {
            "M" -> "남성"
            "F" -> "여성"
            "U" -> "성별 무관"
            else -> "무관"
        }

    val companionTypeText: String
        get() = when (companionType) {
            "FULL" -> "전체 동행"
            "PARTIAL" -> "부분 동행"
            else -> "동행"
        }

    val ageConditionText: String
        get() = if (isAgeAny) {
            "연령 무관 · $preferredGenderText"
        } else {
            "$minAge-$maxAge 세 · $preferredGenderText"
        }

    val remainingSlots: Int
        get() = (recruitCapacity - recruitCount).coerceAtLeast(0)

    val travelPeriodText: String
        get() = "${startDate.toDotDate()} - ${endDate.toDotDate()} ($tripDurationText)"

    val createdDateText: String
        get() = createdAt.substringBefore("T").toDotDate()
}

data class MatchingDetailAuthorUiState(
    val userId: Long = 33,
    val nickname: String = "여행메이트",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val gender: String = "F",
    val ageGroup: Int = 20,
    val isSuperHost: Boolean = false
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "무관"
        }

    val ageText: String
        get() = "${ageGroup}대"
}

data class MatchingDetailParticipantUiState(
    val userId: Long = 33,
    val nickname: String = "여행메이트",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val isHost: Boolean = false,
    val gender: String = "F",
    val ageGroup: Int = 20
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "성별 무관"
        }

    val ageText: String
        get() = "${ageGroup}대"
}

data class MatchingDetailProfileImageUiState(
    val type: String = "AVATAR",
    val url: String? = "https://cdn.example.com/profiles/avatar.png",
    val bgColorId: Int = 3
)

private fun String.toDotDate(): String = replace("-", ".")
