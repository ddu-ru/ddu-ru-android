package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.presentation.feature.chat.component.ChatActionBar
import com.gildongmu.dduru.presentation.feature.chat.component.ChatFilterTab
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomCard
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortDropdown
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortOption

enum class ChatTab(val label: String) {
    ALL("전체"),
    REQUEST("요청함"),
    APPLICATION("신청내역")
}

data class ChatRoomUiModel(
    val id: String,
    val displayName: String,
    val postTitle: String?,
    val participantCount: Int?,
    val lastMessagePreview: String?,
    val lastMessageAt: String?,
    val unreadCount: Int,
    val roomType: ChatRoomType
)

@Composable
fun ChatMainScreen(
    chatRooms: List<ChatRoomUiModel> = emptyList(),
    requestUnreadCount: Int = 0,
    applicationUnreadCount: Int = 0,
    initialTab: ChatTab = ChatTab.ALL,
    initialSort: ChatSortOption = ChatSortOption.ALL,
    initialSortExpanded: Boolean = false,
    onChatRoomClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    var selectedSort by remember { mutableStateOf(initialSort) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = null,
                        tint = DduruTheme.colors.primary,
                        modifier = Modifier
                            .width(46.dp)
                            .height(32.dp)
                    )
                    VerticalDivider(
                        color = DduruTheme.colors.gray300,
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = "채팅",
                        style = DduruTheme.typography.H1,
                        color = DduruTheme.colors.gray500
                    )
                }
                ChatActionBar(
                    onSearchClick = onSearchClick,
                    onMenuClick = onMenuClick
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                ChatFilterTab(
                    label = ChatTab.ALL.label,
                    selected = selectedTab == ChatTab.ALL,
                    onClick = { selectedTab = ChatTab.ALL }
                )
                ChatFilterTab(
                    label = ChatTab.REQUEST.label,
                    selected = selectedTab == ChatTab.REQUEST,
                    unreadCount = requestUnreadCount,
                    onClick = { selectedTab = ChatTab.REQUEST }
                )
                ChatFilterTab(
                    label = ChatTab.APPLICATION.label,
                    selected = selectedTab == ChatTab.APPLICATION,
                    unreadCount = applicationUnreadCount,
                    onClick = { selectedTab = ChatTab.APPLICATION }
                )
            }
        }

        if (selectedTab == ChatTab.ALL) {
            ChatSortDropdown(
                selectedOption = selectedSort,
                onOptionSelected = { selectedSort = it },
                initialExpanded = initialSortExpanded,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 4.dp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(chatRooms) { room ->
                ChatRoomCard(
                    displayName = room.displayName,
                    postTitle = room.postTitle,
                    participantCount = room.participantCount,
                    lastMessagePreview = room.lastMessagePreview,
                    lastMessageAt = room.lastMessageAt,
                    unreadCount = room.unreadCount,
                    roomType = room.roomType,
                    onClick = { onChatRoomClick(room.id) }
                )
            }
        }
    }
}

private val previewRooms = listOf(
    ChatRoomUiModel(
        id = "1",
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 3,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = "2",
        displayName = "제주 애월 2박 3일",
        postTitle = null,
        participantCount = 3,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 3,
        roomType = ChatRoomType.GROUP
    ),
    ChatRoomUiModel(
        id = "3",
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 0,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = "4",
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 0,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = "5",
        displayName = "제주 애월 2박 3일",
        postTitle = null,
        participantCount = 3,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 0,
        roomType = ChatRoomType.GROUP
    )
)

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatScreenAllTabPreview() {
    DduruTheme {
        ChatMainScreen(
            chatRooms = previewRooms,
            requestUnreadCount = 4,
            applicationUnreadCount = 3,
            initialTab = ChatTab.ALL,
            initialSort = ChatSortOption.ALL
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatScreenDropdownOpenPreview() {
    DduruTheme {
        ChatMainScreen(
            chatRooms = previewRooms,
            requestUnreadCount = 4,
            applicationUnreadCount = 3,
            initialTab = ChatTab.ALL,
            initialSort = ChatSortOption.ALL,
            initialSortExpanded = true
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatScreenPrivateSortPreview() {
    DduruTheme {
        ChatMainScreen(
            chatRooms = previewRooms.filter { it.roomType == ChatRoomType.PRIVATE },
            requestUnreadCount = 4,
            applicationUnreadCount = 3,
            initialTab = ChatTab.ALL,
            initialSort = ChatSortOption.PRIVATE
        )
    }
}
