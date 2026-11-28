package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.DduruServiceFailureScreen
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.component.LocalDduruErrorToast
import com.gildongmu.dduru.presentation.feature.mypage.component.BlockedUserItem
import com.gildongmu.dduru.presentation.feature.mypage.model.BlockedUserUiModel
import com.gildongmu.dduru.presentation.feature.mypage.model.toUiModel
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.BlockedListViewModel

@Composable
fun BlockedListRoute(
    onBackClick: () -> Unit,
    viewModel: BlockedListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorToast = LocalDduruErrorToast.current

    LaunchedEffect(Unit) {
        viewModel.errorToast.collect { errorToast.show(it) }
    }

    when (val state = uiState) {
        UiState.Loading, UiState.Empty -> DduruLoadingScreen()
        UiState.Failure -> DduruServiceFailureScreen(
            onRetryClick = viewModel::load,
            onBackClick = onBackClick
        )
        is UiState.Success -> BlockedListScreen(
            users = state.data.users.map { it.toUiModel() },
            onBackClick = onBackClick,
            onProfileClick = {},
            onUnblockClick = viewModel::onUnblockClick,
            onLoadMore = viewModel::loadMore
        )
    }
}

@Composable
fun BlockedListScreen(
    users: List<BlockedUserUiModel>,
    onBackClick: () -> Unit,
    onProfileClick: (Long) -> Unit,
    onUnblockClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "차단 목록",
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
        ) {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                BlockedUserItem(
                    user = user,
                    onProfileClick = { onProfileClick(user.id) },
                    onUnblockClick = { onUnblockClick(user.id) }
                )
            }

            item {
                LaunchedEffect(users.size) { onLoadMore() }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 844, name = "차단 목록")
@Composable
private fun BlockedListScreenPreview() {
    DduruTheme {
        BlockedListScreen(
            users = List(3) { index ->
                BlockedUserUiModel(
                    id = index.toLong(),
                    username = "username",
                    introduction = "음식 관심 많아요"
                )
            },
            onBackClick = {},
            onProfileClick = {},
            onUnblockClick = {}
        )
    }
}
