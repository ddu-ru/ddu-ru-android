package com.gildongmu.dduru.presentation.feature.chat.uistate

import com.gildongmu.dduru.domain.entity.chat.ChatRoomType

enum class MessagePosition { HEAD, MIDDLE, TAIL, SINGLE }

enum class ChatMessageKind { TEXT, IMAGE, SYSTEM }

data class ChatMessageUiState(
    val id: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val time: String,
    val unreadCount: Int,
    val isMine: Boolean,
    val position: MessagePosition,
    val kind: ChatMessageKind = ChatMessageKind.TEXT,
    val imageUrls: List<String> = emptyList()
)

sealed interface ChatListItem {
    data class DateDivider(val date: String) : ChatListItem
    data class Message(val message: ChatMessageUiState) : ChatListItem
}

data class ChatRoomUiState(
    val roomType: ChatRoomType = ChatRoomType.PRIVATE,
    val roomName: String = "",
    val postTitle: String = "",
    val participantCount: Int = 0,
    val items: List<ChatListItem> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRetryable: Boolean = false,
    val isInputEnabled: Boolean = true,
    val isAttachPanelOpen: Boolean = false,
    val isUploadingImage: Boolean = false
)
