package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.component.MatchingFilterChip
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel

@Composable
fun PostManagementList(
    items: List<MatchingPostCardUiModel>,
    selectedSort: String,
    showAuthor: Boolean,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(26.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchingFilterChip(
                text = "필터",
                leadingIconRes = R.drawable.ic_filter_funnel,
                onClick = onFilterClick
            )

            MatchingFilterChip(
                text = selectedSort,
                trailingIconRes = R.drawable.ic_chevron_down,
                onClick = onSortClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                ListCap(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
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
                ListCap(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            }
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
