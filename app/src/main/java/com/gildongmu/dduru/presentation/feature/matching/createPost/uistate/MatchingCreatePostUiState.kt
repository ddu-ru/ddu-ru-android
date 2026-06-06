package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class MatchingCreatePostUiState(
    val currentStep: MatchingCreatePostStep = MatchingCreatePostStep.LOCATION,
    val locationSearchUiState: LocationSearchUiState = LocationSearchUiState(),
    val dateSectionUiState: DateSectionUiState = DateSectionUiState(),
    val recruitmentSectionUiState: RecruitmentScreenUiState = RecruitmentScreenUiState(),
    val ageSectionUiState: AgeSectionUiState = AgeSectionUiState(),
    val postWritingUiState: PostWritingUiState = PostWritingUiState(),
    val thumbnailAndTagUiState: ThumbnailAndTagUiState = ThumbnailAndTagUiState(),
    val popularTags: List<String> = emptyList()
) {
    val region: String?
        get() = locationSearchUiState.selectedLocation?.city

    val country: String?
        get() = locationSearchUiState.selectedLocation?.countryName

    val isFirstStep: Boolean
        get() = currentStep == MatchingCreatePostStep.LOCATION

    val hasBottomButton: Boolean
        get() = currentStep.hasBottomButton

    val hasBackButton: Boolean
        get() = currentStep.hasBackButton

    val nextButtonWeight: Float
        get() = if (hasBackButton) 1f else 2f

    val nextButtonText: String
        get() = currentStep.nextButtonText

    val isNextEnabled: Boolean
        get() = when (currentStep) {
            MatchingCreatePostStep.LOCATION -> locationSearchUiState.isNextEnabled
            MatchingCreatePostStep.DATE -> dateSectionUiState.isNextEnabled
            MatchingCreatePostStep.RECRUITMENT_COUNT -> recruitmentSectionUiState.isNextEnabled
            MatchingCreatePostStep.RECRUITMENT_AGE -> ageSectionUiState.isNextEnabled
            MatchingCreatePostStep.POST_WRITING -> postWritingUiState.isNextEnabled
            MatchingCreatePostStep.THUMBNAIL_AND_TAG -> thumbnailAndTagUiState.isNextEnabled
        }
}
