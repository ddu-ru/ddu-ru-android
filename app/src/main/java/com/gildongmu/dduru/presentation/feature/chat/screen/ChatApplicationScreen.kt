package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCard
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCardAction
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSectionHeader
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatApplicationSideEffect
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.SentApplicationStatus
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatApplicationViewModel

@Composable
fun ChatApplicationRoute(
    onNavigateToChatRoom: (Long) -> Unit,
    viewModel: ChatApplicationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatApplicationSideEffect.NavigateToChatRoom -> onNavigateToChatRoom(effect.chatRoomId)
            }
        }
    }

    when (val state = uiState) {
        is UiState.Loading -> DduruLoadingScreen()
        is UiState.Success -> ChatApplicationScreen(
            uiState = state.data,
            onCancelClick = viewModel::onCancelClick,
            onEnterClick = viewModel::onEnterClick
        )
        is UiState.Failure -> DduruFailureScreen(message = "신청 내역을 불러올 수 없습니다.")
        else -> Unit
    }
}

@Composable
private fun ChatApplicationScreen(
    uiState: ChatApplicationUiState,
    onCancelClick: (String) -> Unit,
    onEnterClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        uiState.sections.forEach { section ->
            item {
                ChatSectionHeader(
                    title = section.sectionTitle,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp).padding(top = 12.dp)
                )
            }
            items(section.items, key = { it.id }) { item ->
                when (item.status) {
                    SentApplicationStatus.CONTACTING -> ApplicationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "연락중",
                        statusBgColor = DduruTheme.colors.secondary,
                        statusTextColor = DduruTheme.colors.primary,
                        action = ApplicationCardAction.CANCEL,
                        onActionClick = { onCancelClick(item.id) }
                    )
                    SentApplicationStatus.WAITING -> ApplicationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "연락 대기중",
                        statusBgColor = DduruTheme.colors.gray200,
                        statusTextColor = DduruTheme.colors.gray500,
                        action = ApplicationCardAction.CANCEL,
                        onActionClick = { onCancelClick(item.id) }
                    )
                    SentApplicationStatus.APPROVED -> ApplicationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "수락됨",
                        statusBgColor = DduruTheme.colors.acceptGreenBg,
                        statusTextColor = DduruTheme.colors.acceptGreen,
                        action = ApplicationCardAction.ENTER,
                        onActionClick = { onEnterClick(item.id) }
                    )
                    SentApplicationStatus.REJECTED -> ApplicationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "거절됨",
                        statusBgColor = DduruTheme.colors.rejectRedBg,
                        statusTextColor = DduruTheme.colors.rejectRed,
                        action = ApplicationCardAction.NONE,
                        onActionClick = {}
                    )
                }
            }
        }
    }
}

private val previewUiState = ChatApplicationUiState(
    sections = listOf(
        ChatApplicationSectionUiState(
            sectionTitle = "대기중",
            items = listOf(
                ChatApplicationItemUiState(
                    id = "1",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = SentApplicationStatus.CONTACTING
                ),
                ChatApplicationItemUiState(
                    id = "2",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = SentApplicationStatus.WAITING
                )
            )
        ),
        ChatApplicationSectionUiState(
            sectionTitle = "확정됨",
            items = listOf(
                ChatApplicationItemUiState(
                    id = "3",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = SentApplicationStatus.APPROVED
                ),
                ChatApplicationItemUiState(
                    id = "4",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = SentApplicationStatus.REJECTED
                )
            )
        )
    )
)

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatApplicationScreenPreview() {
    DduruTheme {
        ChatApplicationScreen(
            uiState = previewUiState,
            onCancelClick = {},
            onEnterClick = {}
        )
    }
}
