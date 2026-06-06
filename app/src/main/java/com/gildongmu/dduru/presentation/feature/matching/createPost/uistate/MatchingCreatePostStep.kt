package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.presentation.feature.matching.createPost.model.StepHeaderUiModel

enum class MatchingCreatePostStep(
    val stepNumber: Int,
    val headerTitle: String,
    val headerSubtitle: String?,
    val hasBottomButton: Boolean,
    val nextButtonText: String = "다음",
    val hasBackButton: Boolean = hasBottomButton
) {
    LOCATION(
        stepNumber = 1,
        headerTitle = "어디로 떠나시나요?",
        headerSubtitle = "여행지를 선택해 주세요",
        hasBottomButton = false
    ),
    DATE(
        stepNumber = 2,
        headerTitle = "언제 떠나시나요?",
        headerSubtitle = "날짜를 선택해 주세요.",
        hasBottomButton = true
    ),
    RECRUITMENT_COUNT(
        stepNumber = 3,
        headerTitle = "모집 인원과 동행 형태를 설정해 주세요",
        headerSubtitle = "몇 명이 함께할지, 어떤 방식으로 동행할지 선택해 주세요",
        hasBottomButton = true
    ),
    RECRUITMENT_AGE(
        stepNumber = 4,
        headerTitle = "함께하고 싶은 상대를 알려주세요",
        headerSubtitle = "성별과 연령대를 선택해주세요",
        hasBottomButton = true
    ),
    POST_WRITING(
        stepNumber = 5,
        headerTitle = "제목과 내용을 입력해주세요",
        headerSubtitle = "동행을 찾기 위한 매력적인 글을 작성해 보세요",
        hasBottomButton = true
    ),
    THUMBNAIL_AND_TAG(
        stepNumber = 6,
        headerTitle = "대표 사진과 태그를 설정해 보세요",
        headerSubtitle = "내 글을 더 돋보이게 만들어요",
        hasBottomButton = true,
        nextButtonText = "작성 완료"
    );

    val headerUiModel: StepHeaderUiModel
        get() = StepHeaderUiModel(
            step = stepNumber,
            title = headerTitle,
            subtitle = headerSubtitle
        )

    fun previousOrNull(): MatchingCreatePostStep? = entries.getOrNull(ordinal - 1)

    fun nextOrNull(): MatchingCreatePostStep? = entries.getOrNull(ordinal + 1)
}
