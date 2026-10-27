package com.gildongmu.dduru.presentation.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FloatingBackTopBar
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import com.gildongmu.dduru.presentation.feature.mypage.component.PostManagementList

@Composable
fun WishlistScreen(
    items: List<MatchingPostCardUiModel>,
    selectedSort: String,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
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
            selectedSort = selectedSort,
            showAuthor = true,
            onFilterClick = onFilterClick,
            onSortClick = onSortClick,
            onCardClick = onCardClick,
            onLikeClick = onLikeClick,
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
            selectedSort = "최신순",
            onBackClick = {},
            onFilterClick = {},
            onSortClick = {},
            onCardClick = {},
            onLikeClick = {}
        )
    }
}
