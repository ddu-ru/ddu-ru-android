package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.DduruServiceFailureScreen
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.component.LocalDduruErrorToast
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import com.gildongmu.dduru.presentation.feature.mypage.component.PostManagementList
import com.gildongmu.dduru.presentation.feature.mypage.model.toWishlistCardUiModel
import com.gildongmu.dduru.presentation.feature.mypage.viewmodel.WishlistViewModel

@Composable
fun WishlistRoute(
    onBackClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorToast = LocalDduruErrorToast.current

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.load()
    }

    LaunchedEffect(Unit) {
        viewModel.errorToast.collect { errorToast.show(it) }
    }

    when (val state = uiState) {
        UiState.Loading, UiState.Empty -> DduruLoadingScreen()
        UiState.Failure -> DduruServiceFailureScreen(
            onRetryClick = { viewModel.load() },
            onBackClick = onBackClick
        )
        is UiState.Success -> WishlistScreen(
            items = state.data.posts.map { it.toWishlistCardUiModel() },
            onBackClick = onBackClick,
            onCardClick = onCardClick,
            onLikeClick = viewModel::onUnlikeClick,
            onLoadMore = viewModel::loadMore
        )
    }
}

@Composable
fun WishlistScreen(
    items: List<MatchingPostCardUiModel>,
    onBackClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onLoadMore: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        FloatingBackTopBar(
            title = "찜한 여행",
            onBackClick = onBackClick
        )

        PostManagementList(
            items = items,
            showAuthor = true,
            onCardClick = onCardClick,
            onLikeClick = onLikeClick,
            onLoadMore = onLoadMore,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, heightDp = 844, name = "찜한 여행")
@Composable
private fun WishlistScreenPreview() {
    DduruTheme {
        WishlistScreen(
            items = MatchingUiState.fake.posts.map { it.copy(isLiked = true) },
            onBackClick = {},
            onCardClick = {},
            onLikeClick = {}
        )
    }
}
