package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender

data class AgeSectionUiState(
    val selectedGender: MatchingGender? = null,
    val selectedStartAge: Int? = null,
    val selectedEndAge: Int? = null,
    val isAgeIrrelevant: Boolean = false
) {
    val preferredGender: MatchingGender
        get() = selectedGender ?: MatchingGender.ANY

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
