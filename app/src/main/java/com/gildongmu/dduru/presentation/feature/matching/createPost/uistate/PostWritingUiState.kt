package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

data class PostWritingUiState(
    val title: String = "",
    val content: String = ""
) {
    val isNextEnabled: Boolean
        get() = title.trim().length >= 5 && content.trim().length >= 20
}
