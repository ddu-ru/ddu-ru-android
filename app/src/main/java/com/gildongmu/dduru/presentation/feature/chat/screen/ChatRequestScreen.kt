package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCard
import com.gildongmu.dduru.presentation.feature.chat.component.ApplicationCardAction
import com.gildongmu.dduru.presentation.feature.chat.component.ChatSectionHeader
import com.gildongmu.dduru.presentation.feature.chat.component.ParticipationCard
import com.gildongmu.dduru.presentation.feature.chat.component.ParticipationDialog
import com.gildongmu.dduru.presentation.feature.chat.component.ParticipationDialogType
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestItemUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestSectionUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRequestUiState
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatTab
import com.gildongmu.dduru.presentation.feature.chat.uistate.ReceivedApplicationStatus

@Composable
fun ChatRequestScreen(
    uiState: ChatRequestUiState = ChatRequestUiState(),
    onRejectConfirm: (String) -> Unit = {},
    onAcceptConfirm: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var dialogTargetId by rememberSaveable { mutableStateOf<String?>(null) }
    var dialogTypeName by rememberSaveable { mutableStateOf<String?>(null) }

    val dialogType = dialogTypeName?.let { ParticipationDialogType.valueOf(it) }

    if (dialogTargetId != null && dialogType != null) {
        val id = dialogTargetId!!
        val type = dialogType
        ParticipationDialog(
            type = type,
            onDismiss = {
                dialogTargetId = null
                dialogTypeName = null
            },
            onConfirm = {
                dialogTargetId = null
                dialogTypeName = null
                when (type) {
                    ParticipationDialogType.REJECT -> onRejectConfirm(id)
                    ParticipationDialogType.ACCEPT -> onAcceptConfirm(id)
                    ParticipationDialogType.CONFIRMED -> Unit
                }
            }
        )
    }

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
                    ReceivedApplicationStatus.CONTACTING -> ParticipationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "연락중",
                        onReject = {
                            dialogTargetId = item.id
                            dialogTypeName = ParticipationDialogType.REJECT.name
                        },
                        onAccept = {
                            dialogTargetId = item.id
                            dialogTypeName = ParticipationDialogType.ACCEPT.name
                        }
                    )
                    ReceivedApplicationStatus.WAITING -> ParticipationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "연락 대기중",
                        badgeBackgroundColor = DduruTheme.colors.gray200,
                        badgeTextColor = DduruTheme.colors.gray500,
                        onReject = {
                            dialogTargetId = item.id
                            dialogTypeName = ParticipationDialogType.REJECT.name
                        },
                        onAccept = {
                            dialogTargetId = item.id
                            dialogTypeName = ParticipationDialogType.ACCEPT.name
                        }
                    )
                    ReceivedApplicationStatus.APPROVED -> ApplicationCard(
                        username = item.username,
                        postTitle = item.postTitle,
                        time = item.time,
                        statusLabel = "수락됨",
                        statusBgColor = DduruTheme.colors.acceptGreenBg,
                        statusTextColor = DduruTheme.colors.acceptGreen,
                        action = ApplicationCardAction.NONE,
                        onActionClick = {}
                    )
                    ReceivedApplicationStatus.REJECTED -> ApplicationCard(
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

private val previewUiState = ChatRequestUiState(
    sections = listOf(
        ChatRequestSectionUiState(
            sectionTitle = "연락중",
            items = listOf(
                ChatRequestItemUiState(
                    id = "1",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = ReceivedApplicationStatus.CONTACTING
                ),
                ChatRequestItemUiState(
                    id = "2",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = ReceivedApplicationStatus.WAITING
                )
            )
        ),
        ChatRequestSectionUiState(
            sectionTitle = "완료",
            items = listOf(
                ChatRequestItemUiState(
                    id = "3",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = ReceivedApplicationStatus.APPROVED
                ),
                ChatRequestItemUiState(
                    id = "4",
                    username = "username",
                    postTitle = "제주 애월 2박 3일",
                    time = "방금",
                    status = ReceivedApplicationStatus.REJECTED
                )
            )
        )
    )
)

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatRequestScreenPreview() {
    DduruTheme {
        ChatMainScreen(
            requestUnreadCount = 2,
            initialTab = ChatTab.REQUEST,
            requestContent = {
                ChatRequestScreen(uiState = previewUiState)
            },
            applicationContent = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun ChatRequestScreenDialogPreview() {
    DduruTheme {
        ChatMainScreen(
            requestUnreadCount = 2,
            initialTab = ChatTab.REQUEST,
            requestContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    ChatRequestScreen(uiState = previewUiState)
                    ParticipationDialog(
                        type = ParticipationDialogType.REJECT,
                        onDismiss = {},
                        onConfirm = {}
                    )
                }
            },
            applicationContent = {}
        )
    }
}
