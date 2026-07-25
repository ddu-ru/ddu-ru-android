package com.gildongmu.dduru.presentation.feature.matching.post.uistate

enum class MatchingPostRecruitStatus {
    OPEN,
    CLOSED,
    HIDDEN
}

enum class MatchingPostParticipationStatus {
    NONE,
    PENDING,
    APPROVED,
    REJECTED
}

enum class MatchingPostStatus(
    val label: String
) {
    RECRUITING("모집중"),
    CLOSED("모집 마감"),
    HIDDEN("숨김")
}
