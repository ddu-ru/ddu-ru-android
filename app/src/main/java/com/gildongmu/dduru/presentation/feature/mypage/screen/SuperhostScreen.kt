package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.mypage.component.SuperhostPostItem
import com.gildongmu.dduru.presentation.feature.mypage.component.SuperhostTicketSummaryCard
import com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet.SuperhostApplyBottomSheet
import com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet.SuperhostApplySheetSurface
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostPostUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetMode
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetStep
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostSheetUiState
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostUiState

@Composable
fun SuperhostScreen(
    uiState: SuperhostUiState,
    onBackClick: () -> Unit,
    onApplyClick: (Long) -> Unit,
    onExtendClick: (Long) -> Unit,
    onSheetDismiss: () -> Unit,
    onSheetConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasTicket = uiState.ticketCount > 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "슈퍼호스트",
            onBackClick = onBackClick
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp)
        ) {
            SuperhostTicketSummaryCard(
                title = "보유한 티켓",
                description = if (hasTicket) {
                    "게시글을 더 많은 동행에게 보여주세요"
                } else {
                    "사용할 수 있는 티켓이 없어요"
                },
                ticketCount = uiState.ticketCount
            )

            if (uiState.appliedPosts.isNotEmpty()) {
                SuperhostPostSection(
                    title = "적용중",
                    posts = uiState.appliedPosts,
                    isApplied = true,
                    isActionEnabled = hasTicket,
                    onActionClick = onExtendClick
                )
            }

            if (uiState.unappliedPosts.isNotEmpty()) {
                SuperhostPostSection(
                    title = "미적용",
                    posts = uiState.unappliedPosts,
                    isApplied = false,
                    isActionEnabled = hasTicket,
                    onActionClick = onApplyClick
                )
            }
        }
    }

    uiState.sheet?.let { sheet ->
        SuperhostApplyBottomSheet(
            uiState = sheet,
            onDismiss = onSheetDismiss,
            onConfirm = onSheetConfirm
        )
    }
}

@Composable
private fun SuperhostPostSection(
    title: String,
    posts: List<SuperhostPostUiModel>,
    isApplied: Boolean,
    isActionEnabled: Boolean,
    onActionClick: (Long) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.gray600,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DduruTheme.colors.gray0, RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            posts.forEachIndexed { index, post ->
                SuperhostPostItem(
                    post = post,
                    isApplied = isApplied,
                    isActionEnabled = isActionEnabled,
                    showDivider = index != posts.lastIndex,
                    onActionClick = { onActionClick(post.id) }
                )
            }
        }
    }
}

private val PreviewPost = SuperhostPostUiModel(
    id = 1,
    title = "제주도 3박 4일 여행",
    destination = "제주도 한라산",
    dateRange = "03.15 - 03.18",
    capacity = "3/4명",
    viewCount = "344"
)

private val PreviewAppliedPost = PreviewPost.copy(remainingTime = "2일 2시간 23분 남음")

@Composable
private fun SuperhostScreenPreviewHost(uiState: SuperhostUiState) {
    DduruTheme {
        SuperhostScreen(
            uiState = uiState,
            onBackClick = {},
            onApplyClick = {},
            onExtendClick = {},
            onSheetDismiss = {},
            onSheetConfirm = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 812, name = "기본")
@Composable
private fun SuperhostScreenDefaultPreview() {
    SuperhostScreenPreviewHost(
        SuperhostUiState(
            ticketCount = 3,
            unappliedPosts = List(3) { PreviewPost.copy(id = it.toLong()) }
        )
    )
}

@Preview(showBackground = true, heightDp = 1278, name = "적용중")
@Composable
private fun SuperhostScreenAppliedPreview() {
    SuperhostScreenPreviewHost(
        SuperhostUiState(
            ticketCount = 2,
            appliedPosts = List(2) { PreviewAppliedPost.copy(id = it.toLong()) },
            unappliedPosts = List(3) { PreviewPost.copy(id = it.toLong() + 2) }
        )
    )
}

@Preview(showBackground = true, heightDp = 1278, name = "티켓 없음")
@Composable
private fun SuperhostScreenEmptyTicketPreview() {
    SuperhostScreenPreviewHost(
        SuperhostUiState(
            ticketCount = 0,
            appliedPosts = List(2) { PreviewAppliedPost.copy(id = it.toLong()) },
            unappliedPosts = List(3) { PreviewPost.copy(id = it.toLong() + 2) }
        )
    )
}

private val PreviewSheetState = SuperhostSheetUiState(
    post = PreviewPost.copy(
        title = "야호!!! 이거보고 미국갔습니다",
        gender = MatchingGender.ANY,
        companionType = MatchingCompanionType.FULL
    ),
    mode = SuperhostSheetMode.APPLY,
    period = "3일 (72시간)",
    endDate = "2026년 10월 10일(금) 15:30",
    ticketCount = 3
)

@Composable
private fun SuperhostSheetPreviewHost(sheet: SuperhostSheetUiState) {
    DduruTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SuperhostScreen(
                uiState = SuperhostUiState(
                    ticketCount = 3,
                    unappliedPosts = List(3) { PreviewPost.copy(id = it.toLong()) }
                ),
                onBackClick = {},
                onApplyClick = {},
                onExtendClick = {},
                onSheetDismiss = {},
                onSheetConfirm = {}
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(DduruTheme.colors.black.copy(alpha = 0.5f))
            )

            SuperhostApplySheetSurface(
                uiState = sheet,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 812, name = "적용 확인")
@Composable
private fun SuperhostApplyConfirmPreview() {
    SuperhostSheetPreviewHost(PreviewSheetState)
}

@Preview(showBackground = true, heightDp = 812, name = "적용 처리 중")
@Composable
private fun SuperhostApplyLoadingPreview() {
    SuperhostSheetPreviewHost(PreviewSheetState.copy(step = SuperhostSheetStep.LOADING))
}

@Preview(showBackground = true, heightDp = 812, name = "적용 완료")
@Composable
private fun SuperhostApplyCompletePreview() {
    SuperhostSheetPreviewHost(PreviewSheetState.copy(step = SuperhostSheetStep.COMPLETE))
}

@Preview(showBackground = true, heightDp = 812, name = "연장 확인")
@Composable
private fun SuperhostExtendConfirmPreview() {
    SuperhostSheetPreviewHost(
        PreviewSheetState.copy(
            mode = SuperhostSheetMode.EXTEND,
            endDate = "2026년 10월 13일(화) 15:30",
            ticketCount = 2
        )
    )
}
