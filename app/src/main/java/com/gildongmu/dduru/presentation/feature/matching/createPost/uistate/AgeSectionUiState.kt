package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

enum class RecruitmentGenderOption(
    val label: String
) {
    MALE("남자"),
    FEMALE("여자"),
    ANY("상관없음")
}

data class AgeSectionUiState(
    val selectedGender: RecruitmentGenderOption? = null,
    val selectedStartAge: Int? = null,
    val selectedEndAge: Int? = null,
    val isAgeIrrelevant: Boolean = false
) {
    val isNextEnabled: Boolean
        get() = selectedGender != null &&
            (
                isAgeIrrelevant ||
                    (
                        selectedStartAge != null &&
                            selectedEndAge != null
                        )
                )
}
