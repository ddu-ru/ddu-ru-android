package com.gildongmu.dduru.presentation.feature.matching.model

import androidx.compose.runtime.Immutable

@Immutable
enum class MatchingGender(
    val selectionLabel: String,
    val cardLabel: String,
    val code: String
) {
    MALE(
        selectionLabel = "남자",
        cardLabel = "남성",
        code = "M"
    ),
    FEMALE(
        selectionLabel = "여자",
        cardLabel = "여성",
        code = "F"
    ),
    ANY(
        selectionLabel = "상관없음",
        cardLabel = "성별무관",
        code = "U"
    )
}

@Immutable
enum class MatchingCompanionType(
    val selectionLabel: String,
    val cardLabel: String,
    val guideMessage: String,
    val code: String
) {
    FULL(
        selectionLabel = "전체 동행",
        cardLabel = "전체동행",
        guideMessage = "여행 전반을 함께 해요",
        code = "FULL"
    ),
    PARTIAL(
        selectionLabel = "부분 동행",
        cardLabel = "부분동행",
        guideMessage = "여행 일부만 함께 해요",
        code = "PARTIAL"
    ),
    MEAL(
        selectionLabel = "식사 동행",
        cardLabel = "식사동행",
        guideMessage = "식사 약속 위주로 가볍게 만나요",
        code = "PARTIAL"
    )
}
