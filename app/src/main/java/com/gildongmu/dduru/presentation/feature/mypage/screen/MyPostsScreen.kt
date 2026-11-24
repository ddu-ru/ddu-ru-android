package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingFilterChip
import com.gildongmu.dduru.presentation.feature.mypage.component.MyPostManagementItem
import com.gildongmu.dduru.presentation.feature.mypage.component.SuperhostTicketSummaryCard
import com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet.SuperhostInfoBottomSheet
import com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet.SuperhostInfoSheetSurface
import com.gildongmu.dduru.presentation.feature.mypage.model.MyPostManagementUiModel

@Composable
fun MyPostsScreen(
    posts: List<MyPostManagementUiModel>,
    ticketCount: Int,
    selectedSort: String,
    onBackClick: () -> Unit,
    onSuperhostInfoClick: () -> Unit,
    onApplySuperhostClick: () -> Unit,
    onSortClick: () -> Unit,
    onPostClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    showSuperhostInfo: Boolean = false,
    onSuperhostInfoDismiss: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "작성글 관리",
            onBackClick = onBackClick
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 40.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                SuperhostTicketSummaryCard(
                    title = "슈퍼호스트 티켓",
                    description = "게시글을 더 많은 동행에게 보여주세요",
                    ticketCount = ticketCount,
                    onInfoClick = onSuperhostInfoClick,
                    actionText = "슈퍼호스트 적용하기",
                    onActionClick = onApplySuperhostClick
                )
            }

            item {
                MatchingFilterChip(
                    text = selectedSort,
                    trailingIconRes = R.drawable.ic_chevron_down,
                    onClick = onSortClick,
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                )
            }

            itemsIndexed(
                items = posts,
                key = { _, post -> post.id }
            ) { index, post ->
                MyPostManagementItem(
                    post = post,
                    showDivider = index != posts.lastIndex,
                    onClick = { onPostClick(post.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = DduruTheme.colors.gray0,
                            shape = RoundedCornerShape(
                                topStart = if (index == 0) 24.dp else 0.dp,
                                topEnd = if (index == 0) 24.dp else 0.dp,
                                bottomStart = if (index == posts.lastIndex) 24.dp else 0.dp,
                                bottomEnd = if (index == posts.lastIndex) 24.dp else 0.dp
                            )
                        )
                        .padding(
                            top = if (index == 0) 8.dp else 0.dp,
                            bottom = if (index == posts.lastIndex) 8.dp else 0.dp
                        )
                )
            }
        }
    }

    if (showSuperhostInfo) {
        SuperhostInfoBottomSheet(onDismiss = onSuperhostInfoDismiss)
    }
}

private val PreviewPosts = List(4) { index ->
    MyPostManagementUiModel(
        id = index.toLong(),
        title = "야호!!! 이거보고 미국갔습니다",
        destination = "제주도 한라산",
        dateRange = "03.15 - 03.18",
        capacity = "3/4명",
        isRecruiting = index != 2,
        viewCount = "1,242",
        likeCount = "34"
    )
}

@Preview(showBackground = true, heightDp = 844, name = "작성글 관리")
@Composable
private fun MyPostsScreenPreview() {
    DduruTheme {
        MyPostsScreen(
            posts = PreviewPosts,
            ticketCount = 3,
            selectedSort = "최신순",
            onBackClick = {},
            onSuperhostInfoClick = {},
            onApplySuperhostClick = {},
            onSortClick = {},
            onPostClick = {}
        )
    }
}

@Preview(showBackground = true, heightDp = 844, name = "슈퍼호스트 안내")
@Composable
private fun MyPostsScreenSuperhostInfoPreview() {
    DduruTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MyPostsScreen(
                posts = PreviewPosts,
                ticketCount = 3,
                selectedSort = "최신순",
                onBackClick = {},
                onSuperhostInfoClick = {},
                onApplySuperhostClick = {},
                onSortClick = {},
                onPostClick = {}
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(DduruTheme.colors.black.copy(alpha = 0.5f))
            )

            SuperhostInfoSheetSurface(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}
