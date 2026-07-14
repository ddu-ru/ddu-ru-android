package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCard
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCardAction
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSectionHeader
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatApplicationUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatTab
import com.gildongmu.dduru.presentation.feature.chat.uistate.SentApplicationStatus

@Composable
fun ChatApplicationScreen(
    uiState: ChatApplicationUiState = ChatApplicationUiState(),
    onCancelClick: (String) -> Unit = {},
    onEnterClick: (String) -> Unit = {},
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
        ChatMainScreen(
            applicationUnreadCount = 4,
            initialTab = ChatTab.APPLICATION,
            applicationContent = {
                ChatApplicationScreen(uiState = previewUiState)
            }
        )
    }
}
