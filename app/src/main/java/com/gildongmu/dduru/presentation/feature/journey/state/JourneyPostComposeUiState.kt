package com.gildongmu.dduru.presentation.feature.journey.state

import android.net.Uri

data class JourneyPostComposeUiState(
    val username: String = "",
    val createdAt: String = "",
    val profileImageUrl: String? = null,
    val content: String = "",
    val selectedPhotos: List<Uri> = emptyList()
) {
    val canSubmit: Boolean
        get() = content.isNotBlank() && !isOverMaxLength

    val isOverMaxLength: Boolean
        get() = content.length > MAX_POST_LENGTH
}

const val MAX_POST_LENGTH = 1000
const val MAX_PHOTO_COUNT = 4
