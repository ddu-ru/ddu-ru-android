package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class MatchingEditPostUiState(
    val postId: Long? = null,
    val dateSectionUiState: DateSectionUiState = DateSectionUiState(),
    val recruitmentSectionUiState: RecruitmentScreenUiState = RecruitmentScreenUiState(),
    val ageSectionUiState: AgeSectionUiState = AgeSectionUiState(),
    val postWritingUiState: PostWritingUiState = PostWritingUiState(),
    val thumbnailAndTagUiState: ThumbnailAndTagUiState = ThumbnailAndTagUiState(),
    val popularTags: List<String> = emptyList()
) {
    val isSaveEnabled: Boolean
        get() = dateSectionUiState.isNextEnabled &&
            recruitmentSectionUiState.isNextEnabled &&
            ageSectionUiState.isNextEnabled &&
            postWritingUiState.isNextEnabled &&
            thumbnailAndTagUiState.isNextEnabled
}
