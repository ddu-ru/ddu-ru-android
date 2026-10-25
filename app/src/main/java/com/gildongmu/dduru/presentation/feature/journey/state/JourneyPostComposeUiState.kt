package com.gildongmu.dduru.presentation.feature.journey.state

import android.net.Uri
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JourneyPostComposeUiState(
    val username: String = "",
    val createdAt: String = "",
    val profileImageUrl: String? = null,
    val content: String = "",
    val selectedPhotos: ImmutableList<Uri> = persistentListOf(),
    val isSubmitting: Boolean = false
) {
    val canSubmit: Boolean
        get() = content.isNotBlank() && !isOverMaxLength && !isSubmitting

    val isOverMaxLength: Boolean
        get() = content.length > MAX_POST_LENGTH
}

const val MAX_POST_LENGTH = 1000
const val MAX_PHOTO_COUNT = 4
