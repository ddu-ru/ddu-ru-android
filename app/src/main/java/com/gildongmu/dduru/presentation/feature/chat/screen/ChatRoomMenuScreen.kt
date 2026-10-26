package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomActionCard
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomLeaveBottomSheet
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomLeaveContent
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomMenuCard
import com.gildongmu.dduru.presentation.feature.chat.component.ChatRoomSettingsCard
import com.gildongmu.dduru.presentation.feature.chat.component.ContentCollectionCard
import com.gildongmu.dduru.presentation.feature.chat.component.ContentTab
import com.gildongmu.dduru.presentation.feature.chat.component.PollSummary
import com.gildongmu.dduru.presentation.feature.chat.uistate.ChatRoomMenuUiState

@Composable
fun ChatRoomMenuScreen(
    uiState: ChatRoomMenuUiState,
    onBackClick: () -> Unit,
    onJourneyClick: () -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onContentTabSelected: (ContentTab) -> Unit,
    onViewAllContentClick: () -> Unit,
    onReportClick: () -> Unit,
    onLeaveClick: () -> Unit,
    onLeaveDismiss: () -> Unit,
    onLeaveConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "",
            onBackClick = onBackClick
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(PaddingValues(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp))
        ) {
            ChatRoomMenuCard(
                imageUrl = uiState.imageUrl,
                title = uiState.title,
                participantCount = uiState.participantCount,
                onNavigateClick = onJourneyClick,
                modifier = Modifier.fillMaxWidth()
            )

            ChatRoomSettingsCard(
                notificationsEnabled = uiState.notificationsEnabled,
                onNotificationsChange = onNotificationsChange,
                modifier = Modifier.fillMaxWidth()
            )

            ContentCollectionCard(
                selectedTab = uiState.selectedContentTab,
                onTabSelected = onContentTabSelected,
                photoUrls = uiState.photoUrls,
                polls = uiState.polls,
                onViewAllClick = onViewAllContentClick,
                modifier = Modifier.fillMaxWidth()
            )

            ChatRoomActionCard(
                onReportClick = onReportClick,
                onLeaveClick = onLeaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (uiState.isLeaveDialogVisible) {
        ChatRoomLeaveBottomSheet(
            onDismiss = onLeaveDismiss,
            onConfirm = onLeaveConfirm
        )
    }
}

private val previewUiState = ChatRoomMenuUiState(
    title = "제주 애월 2박 3일",
    participantCount = 3,
    photoUrls = List(5) { "" },
    polls = listOf(
        PollSummary("제목", 12, true, "2026년 3월 10일 17:00 종료"),
        PollSummary("제목", 12, true, "2026년 3월 10일 17:00 종료")
    )
)

@Composable
private fun ChatRoomMenuScreenPreviewContent(uiState: ChatRoomMenuUiState) {
    DduruTheme {
        ChatRoomMenuScreen(
            uiState = uiState,
            onBackClick = {},
            onJourneyClick = {},
            onNotificationsChange = {},
            onContentTabSelected = {},
            onViewAllContentClick = {},
            onReportClick = {},
            onLeaveClick = {},
            onLeaveDismiss = {},
            onLeaveConfirm = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 1000, name = "메뉴 - 사진")
@Composable
private fun ChatRoomMenuScreenPhotoPreview() {
    ChatRoomMenuScreenPreviewContent(previewUiState)
}

@Preview(showBackground = true, heightDp = 1000, name = "메뉴 - 투표")
@Composable
private fun ChatRoomMenuScreenPollPreview() {
    ChatRoomMenuScreenPreviewContent(previewUiState.copy(selectedContentTab = ContentTab.POLL))
}

@Preview(showBackground = true, heightDp = 812, name = "메뉴 - 나가기")
@Composable
private fun ChatRoomMenuScreenLeavePreview() {
    DduruTheme {
        Box {
            ChatRoomMenuScreenPreviewContent(previewUiState)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )
            ChatRoomLeaveContent(
                onDismiss = {},
                onConfirm = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        DduruTheme.colors.gray0,
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
            )
        }
    }
}
