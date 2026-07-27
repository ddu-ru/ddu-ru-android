package com.gildongmu.dduru.presentation.feature.chat.uistate

enum class SentApplicationStatus {
    CONTACTING,
    WAITING,
    APPROVED,
    REJECTED
}

data class ChatApplicationItemUiState(
    val id: String,
    val username: String,
    val postTitle: String,
    val time: String,
    val status: SentApplicationStatus
)

data class ChatApplicationSectionUiState(
    val sectionTitle: String,
    val items: List<ChatApplicationItemUiState>
)

data class ChatApplicationUiState(
    val sections: List<ChatApplicationSectionUiState> = emptyList()
)
