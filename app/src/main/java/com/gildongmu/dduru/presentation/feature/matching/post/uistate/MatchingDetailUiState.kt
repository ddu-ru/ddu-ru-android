package com.gildongmu.dduru.presentation.feature.matching.post.uistate

import com.gildongmu.dduru.core.common.extension.toDotDate

data class MatchingDetailUiState(
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val status: MatchingPostRecruitStatus = MatchingPostRecruitStatus.OPEN,
    val isFull: Boolean = false,
    val startDate: String = "",
    val endDate: String = "",
    val destination: String = "",
    val recruitCapacity: Int = 0,
    val recruitCount: Int = 0,
    val preferredGender: String = "",
    val minAge: Int? = null,
    val maxAge: Int? = null,
    val photoUrl: String? = null,
    val tags: List<String> = emptyList(),
    val viewCount: Int? = null,
    val likeCount: Int = 0,
    val createdAt: String? = null,
    val author: MatchingDetailAuthorUiState = MatchingDetailAuthorUiState(),
    val isOwner: Boolean = false,
    val canEditPost: Boolean = false,
    val hasLiked: Boolean = false,
    val participants: List<MatchingDetailParticipantUiState> = emptyList(),
    val myParticipationStatus: MatchingPostParticipationStatus = MatchingPostParticipationStatus.NONE,
    val isContactRequesting: Boolean = false,
    val tripDurationText: String = "",
    val recruitDeadlineDDay: String = "",
    val companionType: String = ""
) {
    val preferredGenderText: String
        get() = when (preferredGender) {
            "M" -> "남성"
            "F" -> "여성"
            "U" -> "성별 무관"
            else -> "상관 없음"
        }

    val companionTypeText: String
        get() = when (companionType) {
            "FULL" -> "전체 동행"
            "PARTIAL" -> "부분 동행"
            "MEAL" -> "식사 동행"
            else -> "상관 없음"
        }

    val ageConditionText: String
        get() = if (minAge != null && maxAge != null) {
            "$minAge-$maxAge 세 · $preferredGenderText"
        } else {
            preferredGenderText
        }

    val remainingSlots: Int
        get() = (recruitCapacity - recruitCount).coerceAtLeast(0)

    val travelPeriodText: String
        get() = listOfNotNull(
            startDate.takeIf(String::isNotBlank)?.toDotDate(),
            endDate.takeIf(String::isNotBlank)?.toDotDate()
        ).joinToString(" - ").let { period ->
            if (tripDurationText.isBlank()) period else "$period ($tripDurationText)"
        }

    val createdDateText: String?
        get() = createdAt?.substringBefore("T")?.takeIf(String::isNotBlank)?.toDotDate()
}

data class MatchingDetailAuthorUiState(
    val userId: Long = 0L,
    val nickname: String = "",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val gender: String = "U",
    val ageGroup: Int? = null,
    val isSuperHost: Boolean = false
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "무관"
        }

    val ageText: String
        get() = ageGroup?.let { "${it}대" } ?: "연령 미상"
}

data class MatchingDetailParticipantUiState(
    val userId: Long = 0L,
    val nickname: String = "",
    val profileImage: MatchingDetailProfileImageUiState = MatchingDetailProfileImageUiState(),
    val isHost: Boolean = false,
    val gender: String = "U",
    val ageGroup: Int? = null
) {
    val genderText: String
        get() = when (gender) {
            "M" -> "남성"
            "F" -> "여성"
            else -> "성별 무관"
        }

    val ageText: String
        get() = ageGroup?.let { "${it}대" } ?: "연령 미상"
}

data class MatchingDetailProfileImageUiState(
    val type: String = "AVATAR",
    val url: String? = null,
    val bgColorId: Int = 3
)
