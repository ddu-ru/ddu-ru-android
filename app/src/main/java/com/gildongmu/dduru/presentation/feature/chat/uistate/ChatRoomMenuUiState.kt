package com.gildongmu.dduru.presentation.feature.chat.uistate

import com.gildongmu.dduru.presentation.feature.chat.component.ContentTab
import com.gildongmu.dduru.presentation.feature.chat.component.PollSummary

data class ChatRoomMenuUiState(
    val imageUrl: String? = null,
    val title: String = "",
    val participantCount: Int = 0,
    val notificationsEnabled: Boolean = true,
    val selectedContentTab: ContentTab = ContentTab.PHOTO,
    val photoUrls: List<String> = emptyList(),
    val polls: List<PollSummary> = emptyList(),
    val isLeaveDialogVisible: Boolean = false
)
