package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.feature.matching.createPost.util.TagValidator

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

    fun addTag(): ThumbnailAndTagUiState {
        val tag = tagInput.trim()
        val validationMessage = when {
            tag.isBlank() -> null
            addedTags.size >= MAX_TAG_COUNT -> TAG_LIMIT_ERROR_MESSAGE
            else -> TagValidator.validate(tag)
        }

        if (tag.isBlank() || validationMessage != null) {
            return copy(tagValidationMessage = validationMessage)
        }

        return copy(
            tagInput = "",
            tagValidationMessage = null,
            addedTags = addedTags + tag
        )
    }

    fun removeTag(tag: String): ThumbnailAndTagUiState {
        val updatedTags = addedTags.filterNot { it == tag }
        val validationMessage = if (tagInput.trim().isBlank()) {
            null
        } else {
            TagValidator.validate(tagInput.trim())
        }

        return copy(
            addedTags = updatedTags,
            tagValidationMessage = validationMessage
        )
    }

    private companion object {
        const val MAX_TAG_COUNT = 10
        const val TAG_LIMIT_ERROR_MESSAGE = "태그는 최대 10개까지 추가할 수 있어요."
    }
}
