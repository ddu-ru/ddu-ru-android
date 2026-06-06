package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class MatchingCreatePostUiState(
    val currentStep: Int = 1,
    val region: String? = null,
    val country: String? = null,
    val ageSectionUiState: AgeSectionUiState = AgeSectionUiState()
) {
    val isNextEnabled: Boolean
        get() = when (currentStep) {
            1 -> true
            2 -> true
            3 -> true
            4 -> true
            5 -> true
            6 -> true
            else -> false
        }

    companion object {
        const val MAX_STEP = 6
    }
}
