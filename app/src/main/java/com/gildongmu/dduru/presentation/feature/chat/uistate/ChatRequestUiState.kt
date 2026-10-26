package com.gildongmu.dduru.presentation.feature.chat.uistate

enum class ReceivedApplicationStatus {
    CONTACTING,
    WAITING,
    APPROVED,
    REJECTED
}

data class ChatRequestItemUiState(
    val id: String,
    val username: String,
    val postTitle: String,
    val time: String,
    val status: ReceivedApplicationStatus
)

data class ChatRequestSectionUiState(
    val sectionTitle: String,
    val items: List<ChatRequestItemUiState>
)

data class ChatRequestUiState(
    val sections: List<ChatRequestSectionUiState> = emptyList(),
    val isConfirmedDialogVisible: Boolean = false,
    val confirmedGroupRoomId: Long? = null
)
