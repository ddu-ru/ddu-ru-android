package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationFilter
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState

@Composable
fun HomeNotificationFilterRow(
    selectedFilter: HomeNotificationFilter,
    unreadCount: Int,
    onFilterClick: (HomeNotificationFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = HomeNotificationFilter.entries,
            key = { filter -> filter.name }
        ) { filter ->
            HomeNotificationFilterChip(
                label = filter.label,
                count = if (filter == HomeNotificationFilter.UNREAD) unreadCount else 0,
                isSelected = filter == selectedFilter,
                onClick = { onFilterClick(filter) }
            )
        }
    }
}

@Composable
private fun HomeNotificationFilterChip(
    label: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)
    val backgroundColor = if (isSelected) {
        DduruTheme.colors.black
    } else {
        DduruTheme.colors.gray50
    }
    val textColor = if (isSelected) {
        DduruTheme.colors.gray0
    } else {
        DduruTheme.colors.gray600
    }

    Row(
        modifier = modifier
            .height(40.dp)
            .background(color = backgroundColor, shape = shape)
            .then(
                if (isSelected) {
                    Modifier
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray100,
                        shape = shape
                    )
                }
            )
            .clip(shape)
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body4,
            color = textColor
        )

        if (!isSelected && count > 0) {
            HomeNotificationCountBadge(count = count)
        }
    }
}

@Composable
private fun HomeNotificationCountBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(16.dp)
            .background(
                color = DduruTheme.colors.badgeOrange,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            style = DduruTheme.typography.Micro2,
            color = DduruTheme.colors.gray0
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationFilterRowPreview() {
    val uiState = HomeNotificationUiState.preview

    DduruTheme {
        HomeNotificationFilterRow(
            selectedFilter = uiState.selectedFilter,
            unreadCount = uiState.unreadCount,
            onFilterClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
