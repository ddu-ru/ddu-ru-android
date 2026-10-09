package com.gildongmu.dduru.presentation.feature.journey.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruIconButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCommentInput
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyCommentItem
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentItemUiState
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentUiState
import com.gildongmu.dduru.presentation.feature.journey.viewmodel.JourneyCommentViewModel

@Composable
fun JourneyCommentRoute(
    post: JourneyCommentItemUiState,
    comments: List<JourneyCommentItemUiState>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onMoreClick: (Long) -> Unit = {},
    viewModel: JourneyCommentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(post, comments) {
        viewModel.setInitialContent(post, comments)
    }

    JourneyCommentScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCommentTextChange = viewModel::onCommentTextChange,
        onSendClick = viewModel::onSendClick,
        onReplyClick = viewModel::onReplyClick,
        onPinClick = viewModel::onPinClick,
        onMoreClick = onMoreClick,
        modifier = modifier,
    )
}

@Composable
fun JourneyCommentScreen(
    uiState: JourneyCommentUiState,
    onCommentTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onBackClick: () -> Unit,
    onReplyClick: (JourneyCommentItemUiState) -> Unit,
    onPinClick: (Long) -> Unit,
    onMoreClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding(),
        containerColor = DduruTheme.colors.gray50,
        topBar = { JourneyCommentTopBar(onBackClick = onBackClick) },
        bottomBar = {
            JourneyCommentInput(
                value = uiState.commentText,
                onValueChange = onCommentTextChange,
                onSendClick = onSendClick,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            uiState.post?.let { post ->
                item(key = "post-${post.id}") {
                    JourneyCommentItem(
                        comment = post,
                        onReplyClick = { onReplyClick(post) },
                        onPinClick = { onPinClick(post.id) },
                        onMoreClick = { onMoreClick(post.id) },
                    )

                    HorizontalDivider(color = DduruTheme.colors.gray100)
                }
            }

            item(key = "comment-count") {
                Text(
                    text = "댓글 ${uiState.comments.size}",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray846,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp),
                )
            }

            items(
                items = uiState.comments,
                key = { it.id },
            ) { comment ->
                JourneyCommentItem(
                    comment = comment,
                    onReplyClick = { onReplyClick(comment) },
                    onPinClick = { onPinClick(comment.id) },
                    onMoreClick = { onMoreClick(comment.id) },
                )

                HorizontalDivider(color = DduruTheme.colors.gray100)
            }
        }
    }
}

@Composable
private fun JourneyCommentTopBar(
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
    ) {
        DduruIconButton(
            onClick = onBackClick,
            iconRes = R.drawable.ic_chevron_left,
            iconSize = 24.dp,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        Text(
            text = "댓글",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
private fun JourneyCommentScreenPreview() {
    var commentText by remember { mutableStateOf("") }
    val comments = List(12) { index ->
        JourneyCommentItemUiState(
            id = (index + 2).toLong(),
            username = "여행자 ${index + 1}",
            createdAt = "4월 25일 12:23",
            content = "여행 정보가 정말 유용하네요. 감사합니다!",
            isPinned = index == 0,
        )
    }

    JourneyCommentScreen(
        uiState = JourneyCommentUiState(
            post = JourneyCommentItemUiState(
                id = 1L,
                username = "여행지기",
                createdAt = "4월 25일 12:23",
                content = "후쿠오카 여행 관련해서 궁금한 점을 남겨주세요.",
            ),
            comments = comments,
            commentText = commentText,
        ),
        onCommentTextChange = { commentText = it },
        onSendClick = {},
        onBackClick = {},
        onReplyClick = {},
        onPinClick = {},
        onMoreClick = {},
    )
}
