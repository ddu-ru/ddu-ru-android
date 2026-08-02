package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import com.gildongmu.dduru.presentation.feature.chat.component.ChatDateDivider
import com.gildongmu.dduru.presentation.feature.chat.component.GroupMessageBubble
import com.gildongmu.dduru.presentation.feature.chat.component.MessageBubble
import com.gildongmu.dduru.presentation.feature.chat.component.MessageInput
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatRoomSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatListItem
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatMessageUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.MessagePosition
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatRoomViewModel

@Composable
fun ChatRoomRoute(
    chatRoomId: Long,
    onBackClick: () -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(chatRoomId) {
        viewModel.loadMessages(chatRoomId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatRoomSideEffect.NavigateBack -> onBackClick()
                is ChatRoomSideEffect.NavigateToMembers -> Unit
                is ChatRoomSideEffect.NavigateToMenu -> Unit
            }
        }
    }

    ChatRoomScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onMembersClick = viewModel::onMembersClick,
        onMenuClick = viewModel::onMenuClick,
        onPlusClick = viewModel::onPlusClick,
        onSendClick = viewModel::onSendClick,
        onInputChange = viewModel::onInputChange
    )
}

@Composable
private fun ChatRoomScreen(
    uiState: ChatRoomUiState = ChatRoomUiState(),
    onBackClick: () -> Unit,
    onMembersClick: () -> Unit,
    onMenuClick: () -> Unit,
    onPlusClick: () -> Unit,
    onSendClick: (String) -> Unit,
    onInputChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
    ) {
        ChatRoomTopBar(
            roomType = uiState.roomType,
            roomName = uiState.roomName,
            postTitle = uiState.postTitle,
            participantCount = uiState.participantCount,
            onBackClick = onBackClick,
            onMembersClick = onMembersClick,
            onMenuClick = onMenuClick
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            reverseLayout = false,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            items(uiState.items) { item ->
                ChatMessageItem(item = item, roomType = uiState.roomType)
            }
        }

        MessageInput(
            value = uiState.inputText,
            onValueChange = onInputChange,
            onPlusClick = onPlusClick,
            onSendClick = {
                if (uiState.inputText.isNotBlank()) onSendClick(uiState.inputText)
            },
            modifier = Modifier.navigationBarsPadding()
        )
    }
}

@Composable
private fun ChatMessageItem(
    item: ChatListItem,
    roomType: ChatRoomType
) {
    when (item) {
        is ChatListItem.DateDivider -> ChatDateDivider(date = item.date)
        is ChatListItem.Message -> {
            val msg = item.message
            val isHead = msg.position == MessagePosition.HEAD || msg.position == MessagePosition.SINGLE
            val isTail = msg.position == MessagePosition.TAIL || msg.position == MessagePosition.SINGLE
            val showTime = msg.position == MessagePosition.TAIL || msg.position == MessagePosition.SINGLE
            val time = if (showTime) msg.time else ""

            when {
                msg.isMine -> MessageBubble(
                    message = msg.content,
                    time = time,
                    unreadCount = msg.unreadCount,
                    isMine = true,
                    isHead = isHead,
                    isTail = isTail
                )
                roomType == ChatRoomType.GROUP -> GroupMessageBubble(
                    username = msg.senderName,
                    message = msg.content,
                    time = time,
                    unreadCount = msg.unreadCount,
                    isHead = isHead,
                    isTail = isTail
                )
                else -> MessageBubble(
                    message = msg.content,
                    time = time,
                    unreadCount = msg.unreadCount,
                    isMine = false,
                    isHead = isHead,
                    isTail = isTail
                )
            }
        }
    }
}

@Composable
private fun ChatRoomTopBar(
    roomType: ChatRoomType,
    roomName: String,
    postTitle: String,
    participantCount: Int,
    onBackClick: () -> Unit,
    onMembersClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val frostedShape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .dduruDualShadow(firstAlpha = 0.10f, secondAlpha = 0.08f, shape = frostedShape)
                    .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.30f), shape = frostedShape)
                    .background(color = DduruTheme.colors.gray0.copy(alpha = 0.50f), shape = frostedShape)
                    .noRippleClickable(onClick = onBackClick)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_left),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DduruTheme.colors.black
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = roomName,
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.black
                )
                if (roomType == ChatRoomType.GROUP) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home_users),
                            contentDescription = null,
                            tint = DduruTheme.colors.gray500,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = participantCount.toString(),
                            style = DduruTheme.typography.Caption2,
                            color = DduruTheme.colors.gray500
                        )
                    }
                } else {
                    Text(
                        text = postTitle,
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.gray500
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .dduruDualShadow(firstAlpha = 0.10f, secondAlpha = 0.08f, shape = frostedShape)
                .border(width = 1.dp, color = DduruTheme.colors.gray0.copy(alpha = 0.30f), shape = frostedShape)
                .background(color = DduruTheme.colors.gray0.copy(alpha = 0.50f), shape = frostedShape)
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_users_02),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onClick = onMembersClick),
                tint = DduruTheme.colors.black
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onClick = onMenuClick),
                tint = DduruTheme.colors.black
            )
        }
    }
}

private fun figmaPreviewItems() = listOf(
    ChatListItem.Message(
        ChatMessageUiState(
            id = "1",
            senderId = "me",
            senderName = "나",
            content = "톡방 생성",
            time = "",
            unreadCount = 0,
            isMine = true,
            position = MessagePosition.HEAD
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "2",
            senderId = "me",
            senderName = "나",
            content = "여러분 안녕하세요",
            time = "15:22",
            unreadCount = 1,
            isMine = true,
            position = MessagePosition.TAIL
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "3",
            senderId = "other",
            senderName = "username",
            content = "head는 이렇게",
            time = "",
            unreadCount = 0,
            isMine = false,
            position = MessagePosition.HEAD
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "4",
            senderId = "other",
            senderName = "username",
            content = "같은시간에 올라온거 4px",
            time = "",
            unreadCount = 0,
            isMine = false,
            position = MessagePosition.MIDDLE
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "5",
            senderId = "other",
            senderName = "username",
            content = "중간에 들어가는 친구들은 이렇게",
            time = "",
            unreadCount = 0,
            isMine = false,
            position = MessagePosition.MIDDLE
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "6",
            senderId = "other",
            senderName = "username",
            content = "tail은 이렇게",
            time = "15:22",
            unreadCount = 1,
            isMine = false,
            position = MessagePosition.TAIL
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "7",
            senderId = "me",
            senderName = "나",
            content = "일단 기준은 220으로. 그래서 길어지면 이정도로 표시됩니다.",
            time = "15:22",
            unreadCount = 1,
            isMine = true,
            position = MessagePosition.SINGLE
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "8",
            senderId = "other2",
            senderName = "username",
            content = "시간 다르면 다르게 뜨도록",
            time = "15:22",
            unreadCount = 1,
            isMine = false,
            position = MessagePosition.SINGLE
        )
    ),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "9",
            senderId = "other3",
            senderName = "username",
            content = "간격에 주의할것",
            time = "15:23",
            unreadCount = 1,
            isMine = false,
            position = MessagePosition.SINGLE
        )
    ),
    ChatListItem.DateDivider("2025년 4월 22일"),
    ChatListItem.Message(
        ChatMessageUiState(
            id = "10",
            senderId = "me",
            senderName = "나",
            content = "기본 모양은 이렇게",
            time = "15:22",
            unreadCount = 1,
            isMine = true,
            position = MessagePosition.SINGLE
        )
    )
)

@Preview(showBackground = true)
@Composable
private fun ChatRoomScreenPrivatePreview() {
    DduruTheme {
        var inputText by remember { mutableStateOf("") }
        ChatRoomScreen(
            uiState = ChatRoomUiState(
                roomType = ChatRoomType.PRIVATE,
                roomName = "코끼리 아저씨",
                postTitle = "제주 애월 2박 3일",
                participantCount = 2,
                inputText = inputText,
                items = figmaPreviewItems()
            ),
            onBackClick = {},
            onMembersClick = {},
            onMenuClick = {},
            onPlusClick = {},
            onSendClick = {},
            onInputChange = { inputText = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomScreenGroupPreview() {
    DduruTheme {
        var inputText by remember { mutableStateOf("안녕하세요!") }
        ChatRoomScreen(
            uiState = ChatRoomUiState(
                roomType = ChatRoomType.GROUP,
                roomName = "제주 애월 2박 3일",
                postTitle = "제주 애월 2박 3일",
                participantCount = 3,
                inputText = inputText,
                items = figmaPreviewItems()
            ),
            onBackClick = {},
            onMembersClick = {},
            onMenuClick = {},
            onPlusClick = {},
            onSendClick = {},
            onInputChange = { inputText = it }
        )
    }
}
