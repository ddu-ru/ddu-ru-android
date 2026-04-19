package com.gildongmu.dduru.presentation.feature.tripgroup.creative.uistate

object RecruitmentCountNum {
    const val MIN = 2
    const val MAX = 10
    const val DEFAULT = 2
}

data class RecruitmentCountUiState(
    val count: Int = RecruitmentCountNum.DEFAULT,
) {
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
