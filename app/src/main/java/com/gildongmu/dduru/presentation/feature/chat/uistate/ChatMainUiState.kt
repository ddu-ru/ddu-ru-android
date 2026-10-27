package com.gildongmu.dduru.presentation.feature.chat.uistate

import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortOption
import java.time.LocalDateTime

enum class ChatTab(val label: String) {
    ALL("전체"),
    REQUEST("요청함"),
    APPLICATION("신청내역")
}

data class ChatRoomUiModel(
    val id: Long,
    val displayName: String,
    val postTitle: String?,
    val participantCount: Int?,
    val lastMessagePreview: String?,
    val lastMessageAt: String?,
    val unreadCount: Int,
    val roomType: ChatRoomType,
    val thumbnailUrl: String? = null,
    val activityAt: LocalDateTime? = null
)
data class ChatMainUiState(
    val chatRooms: List<ChatRoomUiModel> = emptyList(),
    val requestUnreadCount: Int = 0,
    val applicationUnreadCount: Int = 0,
    val selectedTab: ChatTab = ChatTab.ALL,
    val selectedSort: ChatSortOption = ChatSortOption.ALL,
    val sortExpanded: Boolean = false,
    val nextCursor: String? = null,
    val hasNext: Boolean = false
)
