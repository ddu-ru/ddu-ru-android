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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.presentation.feature.chat.component.ChatActionBar
import com.gildongmu.dduru.presentation.feature.chat.component.ChatFilterTab
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomCard
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortDropdown
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSortOption
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatMainSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiModel
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatTab
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatMainViewModel

@Composable
fun ChatMainRoute(
    onNavigateToChatRoom: (Long) -> Unit,
    viewModel: ChatMainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatMainSideEffect.NavigateToChatRoom -> onNavigateToChatRoom(effect.chatRoomId)
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.loadChatRooms()
        }
    }

    when (val state = uiState) {
        is UiState.Loading -> DduruLoadingScreen()
        is UiState.Success -> ChatMainScreen(
            chatRooms = state.data.chatRooms,
            onChatRoomClick = { id -> viewModel.onChatRoomClick(id.toLong()) },
            onLoadMore = { viewModel.loadMoreChatRooms() },
            requestContent = { ChatRequestRoute() },
            applicationContent = { ChatApplicationRoute(onNavigateToChatRoom = onNavigateToChatRoom) }
        )
        is UiState.Failure -> DduruFailureScreen(message = "채팅 목록을 불러올 수 없습니다.")
        else -> Unit
    }
}

@Composable
private fun ChatMainScreen(
    chatRooms: List<ChatRoomUiModel> = emptyList(),
    requestUnreadCount: Int = 0,
    applicationUnreadCount: Int = 0,
    initialTab: ChatTab = ChatTab.ALL,
    initialSort: ChatSortOption = ChatSortOption.ALL,
    initialSortExpanded: Boolean = false,
    onChatRoomClick: (Long) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    modifier: Modifier = Modifier,
    requestContent: @Composable () -> Unit,
    applicationContent: @Composable () -> Unit
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    var selectedSort by remember { mutableStateOf(initialSort) }
    var sortExpanded by remember { mutableStateOf(initialSortExpanded) }

    Scaffold(
        modifier = modifier.background(DduruTheme.colors.gray50),
        topBar = {
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                ChatTab.ALL -> {
                    val listState = rememberLazyListState()
                    val shouldLoadMore by remember {
                        derivedStateOf {
                            val lastVisibleIndex =
                                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                            val totalItems = listState.layoutInfo.totalItemsCount
                            totalItems > 0 && lastVisibleIndex >= totalItems - 3
                        }
                    }
                    LaunchedEffect(shouldLoadMore, chatRooms.size) {
                        if (shouldLoadMore) onLoadMore()
                    }

                    ChatSortDropdown(
                        selectedOption = selectedSort,
                        onOptionSelected = { selectedSort = it },
                        expanded = sortExpanded,
                        onExpandedChange = { sortExpanded = it },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 4.dp)
                    )
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                ChatTab.REQUEST -> requestContent()
                ChatTab.APPLICATION -> applicationContent()
            }
        }
    }
}

private val previewRooms = listOf(
    ChatRoomUiModel(
        id = 1L,
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 3,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = 2L,
        displayName = "제주 애월 2박 3일",
        postTitle = null,
        participantCount = 3,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 3,
        roomType = ChatRoomType.GROUP
    ),
    ChatRoomUiModel(
        id = 3L,
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 0,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = 4L,
        displayName = "username",
        postTitle = "제주 애월 2박 3일",
        participantCount = null,
        lastMessagePreview = "Text",
        lastMessageAt = "방금",
        unreadCount = 0,
        roomType = ChatRoomType.PRIVATE
    ),
    ChatRoomUiModel(
        id = 5L,
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
            initialSort = ChatSortOption.ALL,
            requestContent = {},
            applicationContent = {}
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
            initialSortExpanded = true,
            requestContent = {},
            applicationContent = {}
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
            initialSort = ChatSortOption.PRIVATE,
            requestContent = {},
            applicationContent = {}
        )
    }
}
