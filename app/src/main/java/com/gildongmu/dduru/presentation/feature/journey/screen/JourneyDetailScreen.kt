package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruIconButton
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailActionBar
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailFilterSection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailFilterType
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailHeader
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailMemberSection
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailPostItem
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailVoteItem
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyMemberUiState
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyWritingButton
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailPostUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyDetailVoteUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyDetailViewModel
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JourneyDetailRoute(
    journeyId: Long,
    onBackClick: () -> Unit,
    onManageMembersClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onPostCommentClick: (Long) -> Unit = {},
    onPostClick: (Long) -> Unit = {},
    onPostPinClick: (Long) -> Unit = {},
    onPostMoreClick: (Long) -> Unit = {},
    onVoteClick: (Long) -> Unit = {},
    onWritePostClick: () -> Unit = {},
    onWriteScheduleClick: () -> Unit = {},
    onWriteVoteClick: () -> Unit = {},
    viewModel: JourneyDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.refreshJourneyDetail(journeyId)
    }

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> DduruFailureScreen(message = "여정 정보를 불러오지 못했어요.")
        is UiState.Success -> JourneyDetailScreen(
            uiState = state.data,
            onBackClick = onBackClick,
            onManageMembersClick = onManageMembersClick,
            onChatRoomClick = onChatRoomClick,
            onNotificationClick = onNotificationClick,
            onShareClick = onShareClick,
            onMoreClick = onMoreClick,
            onFilterClick = viewModel::onFilterClick,
            onPostCommentClick = onPostCommentClick,
            onPostClick = onPostClick,
            onPostPinClick = viewModel::onPostPinClick,
            onPostMoreClick = onPostMoreClick,
            onVoteClick = onVoteClick,
            onWritePostClick = onWritePostClick,
            onWriteScheduleClick = onWriteScheduleClick,
            onWriteVoteClick = onWriteVoteClick,
            modifier = modifier
        )
    }
}

@Composable
fun JourneyDetailScreen(
    uiState: JourneyDetailUiState,
    onBackClick: () -> Unit,
    onManageMembersClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onNotificationClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onFilterClick: (JourneyDetailFilterType) -> Unit,
    onPostCommentClick: (Long) -> Unit = {},
    onPostClick: (Long) -> Unit = {},
    onPostPinClick: (Long) -> Unit = {},
    onPostMoreClick: (Long) -> Unit = {},
    onVoteClick: (Long) -> Unit = {},
    onWritePostClick: () -> Unit = {},
    onWriteScheduleClick: () -> Unit = {},
    onWriteVoteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DduruTheme.colors.gray0)
        ) {
            item {
                JourneyDetailHeader(
                    imageUrl = uiState.imageUrl,
                    dDay = uiState.dDay,
                    title = uiState.title,
                    period = uiState.period,
                    modifier = Modifier.aspectRatio(375f / 300f)
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-16).dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(
                            color = DduruTheme.colors.gray0,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    JourneyDetailMemberSection(
                        members = uiState.members,
                        canManageMembers = uiState.isOwner,
                        onManageClick = onManageMembersClick
                    )

                    ChatRoomShortcutButton(
                        onClick = onChatRoomClick
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "콘텐츠 모아보기",
                            style = DduruTheme.typography.Title2,
                            color = DduruTheme.colors.gray800
                        )

                        JourneyDetailFilterSection(
                            filters = DETAIL_FILTERS,
                            selectedFilter = uiState.selectedFilter,
                            onFilterClick = onFilterClick
                        )

                        JourneyDetailContentList(
                            posts = uiState.filteredPosts,
                            votes = if (uiState.selectedFilter == JourneyDetailFilterType.VOTE) {
                                uiState.votes
                            } else {
                                emptyList()
                            },
                            onPostCommentClick = onPostCommentClick,
                            onPostClick = onPostClick,
                            onPostPinClick = onPostPinClick,
                            onPostMoreClick = onPostMoreClick,
                            onVoteClick = onVoteClick
                        )
                    }
                }
            }
        }

        DduruIconButton(
            onClick = onBackClick,
            iconRes = R.drawable.ic_chevron_left,
            iconSize = 24.dp,
            backgroundColor = DduruTheme.colors.gray0.copy(alpha = 0.50f),
            showShadow = false,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
        )

        JourneyDetailActionBar(
            onNotificationClick = onNotificationClick,
            onShareClick = onShareClick,
            onMoreClick = onMoreClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            JourneyWritingButton(
                onWritePostClick = onWritePostClick,
                onWriteScheduleClick = onWriteScheduleClick,
                onWriteVoteClick = onWriteVoteClick
            )
        }
    }
}

@Composable
private fun ChatRoomShortcutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DduruButton(
        text = "채팅방 바로 가기",
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        textStyle = DduruTheme.typography.Title2,
        shape = RoundedCornerShape(13.dp)
    )
}

@Composable
private fun JourneyDetailContentList(
    posts: List<JourneyDetailPostUiState>,
    votes: List<JourneyDetailVoteUiState>,
    onPostCommentClick: (Long) -> Unit,
    onPostClick: (Long) -> Unit,
    onPostPinClick: (Long) -> Unit,
    onPostMoreClick: (Long) -> Unit,
    onVoteClick: (Long) -> Unit
) {
    if (posts.isEmpty() && votes.isEmpty()) {
        Text(
            text = "아직 등록된 콘텐츠가 없어요.",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray500,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
        return
    }

    posts.forEach { post ->
        JourneyDetailPostItem(
            profileImageUrl = post.profileImageUrl,
            username = post.username,
            createdAt = post.createdAt,
            title = post.title,
            imageUrls = post.imageUrls,
            commentCount = post.commentCount,
            isPinned = post.isPinned,
            onPostClick = { onPostClick(post.id) },
            onCommentClick = { onPostCommentClick(post.id) },
            onPinClick = { onPostPinClick(post.id) },
            onMoreClick = { onPostMoreClick(post.id) }
        )
    }

    votes.forEach { vote ->
        JourneyDetailVoteItem(
            title = vote.title,
            participantCount = vote.participantCount,
            isInProgress = vote.isInProgress,
            endDate = vote.endDate,
            onClick = { onVoteClick(vote.id) },
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

private val DETAIL_FILTERS = persistentListOf(
    JourneyDetailFilterType.FIXED,
    JourneyDetailFilterType.BOARD,
    JourneyDetailFilterType.SCHEDULE,
    JourneyDetailFilterType.PHOTO,
    JourneyDetailFilterType.VOTE
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JourneyDetailScreenPreview() {
    DduruTheme {
        var selectedFilter by remember { mutableStateOf(JourneyDetailFilterType.FIXED) }

        JourneyDetailScreen(
            uiState = JourneyDetailUiState(
                imageUrl = "https://images.unsplash.com/photo-1528164344705-47542687000d",
                dDay = "D-23",
                title = "후쿠오카 미식 여행",
                period = "2026. 08. 27 - 2026. 08. 30",
                members = listOf(
                    JourneyMemberUiState(1, "둘루", null, "방장"),
                    JourneyMemberUiState(2, "길동", null, "총무"),
                    JourneyMemberUiState(3, "민지", null, "멤버"),
                    JourneyMemberUiState(4, "준호", null, "멤버")
                ),
                posts = listOf(
                    JourneyDetailPostUiState(
                        id = 1,
                        category = JourneyDetailFilterType.BOARD,
                        username = "둘루",
                        createdAt = "10분 전",
                        title = "여행 일정 공유합니다! 맛집 추천도 편하게 남겨주세요.",
                        commentCount = 12,
                        isPinned = true
                    ),
                    JourneyDetailPostUiState(
                        id = 2,
                        category = JourneyDetailFilterType.BOARD,
                        username = "민지",
                        createdAt = "1시간 전",
                        title = "숙소 근처 카페 리스트 공유해요.",
                        commentCount = 3
                    )
                ),
                filteredPosts = listOf(
                    JourneyDetailPostUiState(
                        id = 1,
                        category = JourneyDetailFilterType.BOARD,
                        username = "둘루",
                        createdAt = "10분 전",
                        title = "여행 일정 공유합니다! 맛집 추천도 편하게 남겨주세요.",
                        commentCount = 12,
                        isPinned = true
                    )
                ),
                votes = listOf(
                    JourneyDetailVoteUiState(1, "첫날 저녁 메뉴를 정해주세요!", 8, true, "8월 20일")
                ),
                selectedFilter = selectedFilter
            ),
            onBackClick = {},
            onManageMembersClick = {},
            onChatRoomClick = {},
            onFilterClick = { selectedFilter = it }
        )
    }
}
