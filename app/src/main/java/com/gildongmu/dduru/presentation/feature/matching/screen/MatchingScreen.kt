package com.gildongmu.dduru.presentation.feature.matching.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingFilterBottomSheet
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingPostListSection
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingSearchFilterSection
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingSearchScreen
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingTopHeader
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingWriteButton
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.MatchingSortBottomSheet
import com.gildongmu.dduru.presentation.feature.matching.effect.MatchingSideEffect
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingDestinationUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import com.gildongmu.dduru.presentation.feature.matching.viewmodel.MatchingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchingRoute(
    navigateToNotification: () -> Unit = {},
    navigateToMenu: () -> Unit = {},
    navigateToCreatePost: () -> Unit = {},
    navigateToPostDetail: (Long) -> Unit = {},
    viewModel: MatchingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MatchingSideEffect.NavigateToNotification -> navigateToNotification()
                MatchingSideEffect.NavigateToMenu -> navigateToMenu()
                MatchingSideEffect.NavigateToCreatePost -> navigateToCreatePost()
                is MatchingSideEffect.NavigateToPostDetail -> navigateToPostDetail(sideEffect.id)
            }
        }
    }

    when (val state = uiState) {
        UiState.Empty -> Unit
        UiState.Loading -> DduruLoadingScreen()
        UiState.Failure -> {
            DduruFailureScreen(
                message = "매칭 목록을 불러오지 못했어요."
            )
        }
        is UiState.Success -> {
            MatchingScreen(
                uiState = state.data,
                onNotificationClick = viewModel::onNotificationClick,
                onMenuClick = viewModel::onMenuClick,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onSearchFieldClick = viewModel::onSearchFieldClick,
                onSearchBackClick = viewModel::onSearchBackClick,
                onSearchKeywordClick = viewModel::onSearchKeywordClick,
                onDestinationClick = viewModel::onDestinationClick,
                onRecentSearchKeywordRemove = viewModel::onRecentSearchKeywordRemove,
                onSortSelected = viewModel::onSortSelected,
                onPostClick = viewModel::onPostClick,
                onPostLikeClick = viewModel::onPostLikeClick,
                onWriteClick = viewModel::onWriteClick
            )
        }
    }
}

@Composable
private fun MatchingScreen(
    uiState: MatchingUiState,
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchFieldClick: () -> Unit,
    onSearchBackClick: () -> Unit,
    onSearchKeywordClick: (String) -> Unit,
    onDestinationClick: (MatchingDestinationUiModel) -> Unit,
    onRecentSearchKeywordRemove: (String) -> Unit,
    onSortSelected: (String) -> Unit,
    onPostClick: (Long) -> Unit,
    onPostLikeClick: (Long) -> Unit,
    onWriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFilterSheetVisible by remember { mutableStateOf(false) }
    var isSortSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radius = if (uiState.isSearchScreenVisible) {
                        16.dp
                    } else {
                        0.dp
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0f to DduruTheme.colors.gray0.copy(alpha = 0.8f),
                                0.76f to DduruTheme.colors.gray0.copy(alpha = 0.5f),
                                1f to DduruTheme.colors.gray0.copy(alpha = 0f)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatchingTopHeader(
                    onNotificationClick = onNotificationClick,
                    onMenuClick = onMenuClick
                )

                MatchingSearchFilterSection(
                    searchQuery = uiState.searchQuery,
                    selectedSort = uiState.selectedSort,
                    onSearchQueryChange = onSearchQueryChange,
                    onSearchFieldClick = onSearchFieldClick,
                    onFilterClick = { isFilterSheetVisible = true },
                    onSortClick = { isSortSheetVisible = true },
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            MatchingPostListSection(
                items = uiState.posts,
                onCardClick = onPostClick,
                onLikeClick = onPostLikeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 132.dp
                )
            )
        }

        MatchingWriteButton(
            onClick = onWriteClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .navigationBarsPadding()
                .padding(end = 16.dp)
        )

        MatchingSortBottomSheet(
            visible = isSortSheetVisible,
            selectedSort = uiState.selectedSort,
            onSortSelected = onSortSelected,
            onDismiss = { isSortSheetVisible = false }
        )

        MatchingFilterBottomSheet(
            visible = isFilterSheetVisible,
            onDismiss = { isFilterSheetVisible = false },
            onDestinationClick = {}
        )

        if (uiState.isSearchScreenVisible) {
            MatchingSearchScreen(
                searchQuery = uiState.searchQuery,
                recentKeywords = uiState.recentSearchKeywords,
                popularKeywords = uiState.popularSearchKeywords,
                destinations = uiState.destinationSearchResults,
                isDestinationLoading = uiState.isDestinationSearchLoading,
                hasDestinationSearchResult = uiState.hasDestinationSearchResult,
                onSearchQueryChange = onSearchQueryChange,
                onSearchKeywordClick = onSearchKeywordClick,
                onDestinationClick = onDestinationClick,
                onRecentKeywordRemove = onRecentSearchKeywordRemove,
                onBackClick = onSearchBackClick,
                modifier = Modifier.zIndex(2f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun MatchingScreenPreview() {
    DduruTheme {
        MatchingScreen(
            uiState = MatchingUiState.fake,
            onNotificationClick = {},
            onMenuClick = {},
            onSearchQueryChange = {},
            onSearchFieldClick = {},
            onSearchBackClick = {},
            onSearchKeywordClick = {},
            onDestinationClick = {},
            onRecentSearchKeywordRemove = {},
            onSortSelected = {},
            onPostClick = {},
            onPostLikeClick = {},
            onWriteClick = {}
        )
    }
}
