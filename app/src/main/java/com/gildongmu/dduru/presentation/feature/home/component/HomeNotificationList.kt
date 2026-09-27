package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationItemUiState
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeNotificationList(
    notifications: ImmutableList<HomeNotificationItemUiState>,
    onNotificationClick: (HomeNotificationItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        items(
            items = notifications,
            key = { notification -> notification.id },
            contentType = { HomeNotificationItemUiState::class }
        ) { notification ->
            HomeNotificationListItem(
                notification = notification,
                onClick = { onNotificationClick(notification) }
            )
        }
    }
}

@Composable
private fun HomeNotificationListItem(
    notification: HomeNotificationItemUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick)
            .padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = notification.title,
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray900,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = DduruTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Text(
                    text = notification.body,
                    style = DduruTheme.typography.Body4,
                    color = DduruTheme.colors.gray700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = notification.timeAgo,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray500
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 24.dp),
            color = DduruTheme.colors.gray200
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationListPreview() {
    DduruTheme {
        HomeNotificationList(
            notifications = HomeNotificationUiState.preview.notifications,
            onNotificationClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(horizontal = 16.dp)
        )
    }
}
