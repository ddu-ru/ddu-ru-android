package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

enum class RecruitmentGenderOption(
    val label: String
) {
    MALE("남자"),
    FEMALE("여자"),
    ANY("상관없음");

    val code: String
        get() = when (this) {
            MALE -> "M"
            FEMALE -> "F"
            ANY -> "U"
        }
}

data class AgeSectionUiState(
    val selectedGender: RecruitmentGenderOption? = null,
    val selectedStartAge: Int? = null,
    val selectedEndAge: Int? = null,
    val isAgeIrrelevant: Boolean = false
) {
    val preferredGender: RecruitmentGenderOption
        get() = selectedGender ?: RecruitmentGenderOption.ANY

    val isAgeAny: Boolean
        get() = isAgeIrrelevant

    val minAge: Int?
        get() = selectedStartAge

    val maxAge: Int?
        get() = selectedEndAge

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
