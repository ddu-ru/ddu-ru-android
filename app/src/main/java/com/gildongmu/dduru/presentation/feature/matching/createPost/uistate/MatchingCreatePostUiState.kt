package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class MatchingCreatePostUiState(
    val currentStep: Int = 1,
    val locationSearchUiState: LocationSearchUiState = LocationSearchUiState(),
    val dateSectionUiState: DateSectionUiState = DateSectionUiState(),
    val recruitmentSectionUiState: RecruitmentScreenUiState = RecruitmentScreenUiState(),
    val ageSectionUiState: AgeSectionUiState = AgeSectionUiState(),
    val postWritingUiState: PostWritingUiState = PostWritingUiState(),
    val thumbnailAndTagUiState: ThumbnailAndTagUiState = ThumbnailAndTagUiState()
) {
    val region: String?
        get() = locationSearchUiState.selectedLocation?.city

    val country: String?
        get() = locationSearchUiState.selectedLocation?.countryName

    val isNextEnabled: Boolean
        get() = when (currentStep) {
            1 -> locationSearchUiState.isNextEnabled
            2 -> dateSectionUiState.isNextEnabled
            3 -> recruitmentSectionUiState.isNextEnabled
            4 -> ageSectionUiState.isNextEnabled
            5 -> postWritingUiState.isNextEnabled
            6 -> thumbnailAndTagUiState.isNextEnabled
            else -> false
        }

    companion object {
        const val MAX_STEP = 6
    }
}
