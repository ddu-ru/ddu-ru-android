package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel

@Composable
fun PostManagementList(
    items: List<MatchingPostCardUiModel>,
    showAuthor: Boolean,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 32.dp, bottom = 24.dp)
    ) {
        item {
            ListCap(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        }

        itemsIndexed(
            items = items,
            key = { _, item -> item.id }
        ) { index, item ->
            MyPostCard(
                item = item,
                showDivider = index != items.lastIndex,
                onClick = { onCardClick(item.id) },
                onLikeClick = { onLikeClick(item.id) },
                showAuthor = showAuthor
            )
        }

        item {
            ListCap(shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        }
    }
}

@Composable
private fun ListCap(shape: RoundedCornerShape) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(shape)
            .background(DduruTheme.colors.gray0)
    )
}
