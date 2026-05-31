package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.R

data class ThumbnailAndTagUiState(
    val regionName: String = "후쿠오카",
    val countryName: String = "일본",
    val imageResId: Int = R.drawable.fukuoka,
    val tagInput: String = "",
    val addedTags: List<String> = emptyList()
) {
    val canAddTag: Boolean
        get() = tagInput.trim().isNotBlank() && addedTags.size < 10

    fun addTag(): ThumbnailAndTagUiState {
        val trimmedTag = tagInput.trim()
        if (trimmedTag.isBlank() || addedTags.size >= 10) return this

        return copy(
            addedTags = addedTags + trimmedTag,
            tagInput = ""
        )
    }

    fun removeTag(tag: String): ThumbnailAndTagUiState {
        return copy(
            addedTags = addedTags.filterNot { it == tag }
        )
    }
}
