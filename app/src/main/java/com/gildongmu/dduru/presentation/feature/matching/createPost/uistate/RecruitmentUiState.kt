package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType

object RecruitmentCountNum {
    const val MIN = 2
    const val MAX = 10
    const val DEFAULT = 2
}

data class RecruitmentCountUiState(
    val rawCount: Int = RecruitmentCountNum.DEFAULT
) {
    val count: Int = rawCount.coerceIn(
        RecruitmentCountNum.MIN,
        RecruitmentCountNum.MAX
    )

    val canIncrease: Boolean
        get() = count < RecruitmentCountNum.MAX

    val canDecrease: Boolean
        get() = count > RecruitmentCountNum.MIN

    val recruitmentCountMessage: String
        get() = if (count >= RecruitmentCountNum.MAX) {
            "10명 이상 설정할 수 없어요"
        } else {
            "나를 포함한 총 모집 인원이에요.\n2명 이상 설정해 주세요"
        }
}

data class RecruitmentScreenUiState(
    val recruitmentCount: RecruitmentCountUiState = RecruitmentCountUiState(),
    val selectedCompanionType: MatchingCompanionType? = null,
    val isCompanionTypeOptional: Boolean = false
) {
    val recruitCount: Int
        get() = recruitmentCount.count

    val companionType: MatchingCompanionType
        get() = selectedCompanionType ?: MatchingCompanionType.PARTIAL

    val isNextEnabled: Boolean
        get() = isCompanionTypeOptional || selectedCompanionType != null

    val companionTypeGuideMessage: String?
        get() = if (isCompanionTypeOptional) {
            "어떠한 형태도 다 괜찮아요!"
        } else {
            selectedCompanionType?.guideMessage
        }
}
