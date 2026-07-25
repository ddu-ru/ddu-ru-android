package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.R

data class ThumbnailAndTagUiState(
    val regionName: String = "후쿠오카",
    val countryName: String = "일본",
    val imageResId: Int = R.drawable.fukuoka,
    val previewImageUrl: String? = null,
    val photoUrl: String? = null,
    val tagInput: String = "",
    val tagValidationMessage: String? = null,
    val addedTags: List<String> = emptyList()
) {
    val isNextEnabled: Boolean
        get() = true

    val canAddTag: Boolean
        get() = tagInput.trim().isNotBlank() && tagValidationMessage == null && addedTags.size < 10
}
