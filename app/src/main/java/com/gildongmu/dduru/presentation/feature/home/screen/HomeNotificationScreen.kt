package com.gildongmu.dduru.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.component.HomeNotificationEmptyState
import com.gildongmu.dduru.presentation.feature.home.component.HomeNotificationFilterRow
import com.gildongmu.dduru.presentation.feature.home.component.HomeNotificationList
import com.gildongmu.dduru.presentation.feature.home.component.HomeNotificationMoreMenu
import com.gildongmu.dduru.presentation.feature.home.component.HomeNotificationTopBar
import com.gildongmu.dduru.presentation.feature.home.effect.HomeNotificationSideEffect
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationFilter
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationItemUiState
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState
import com.gildongmu.dduru.presentation.feature.home.viewmodel.HomeNotificationViewModel
import com.gildongmu.dduru.presentation.navigation.RootNavRoute
import com.gildongmu.dduru.presentation.navigation.toPushNotificationRoute
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeNotificationRoute(
    onBackClick: () -> Unit,
    onNavigateToResource: (RootNavRoute) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeNotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is HomeNotificationSideEffect.NavigateToResource -> {
                    sideEffect.resourceType.toPushNotificationRoute(sideEffect.resourceId)
                        ?.let(onNavigateToResource)
                }
            }
        }
    }

    when (val state = uiState) {
        UiState.Loading -> DduruLoadingScreen(modifier = modifier)
        UiState.Failure -> DduruFailureScreen(
            message = "알림을 불러오지 못했어요.",
            modifier = modifier
        )
        is UiState.Success -> HomeNotificationScreen(
            uiState = state.data,
            onBackClick = onBackClick,
            onFilterClick = viewModel::onFilterClick,
            onNotificationClick = viewModel::onNotificationClick,
            onLoadMoreNotifications = viewModel::loadMoreNotifications,
            onMarkAllAsReadClick = viewModel::onMarkAllAsReadClick,
            modifier = modifier
        )
        else -> Unit
    }
}

@Composable
private fun HomeNotificationScreen(
    uiState: HomeNotificationUiState,
    onBackClick: () -> Unit,
    onFilterClick: (HomeNotificationFilter) -> Unit,
    onNotificationClick: (HomeNotificationItemUiState) -> Unit,
    onLoadMoreNotifications: () -> Unit,
    onMarkAllAsReadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    val notifications = uiState.filteredNotifications

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeNotificationTopBar(
                onBackClick = onBackClick,
                onMenuClick = { isMenuVisible = !isMenuVisible },
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)
            )

            HomeNotificationFilterRow(
                selectedFilter = uiState.selectedFilter,
                unreadCount = uiState.unreadCount,
                onFilterClick = onFilterClick,
                modifier = Modifier.padding(top = 24.dp)
            )

            if (uiState.markAllAsReadFailed) {
                Text(
                    text = "전체 확인에 실패했어요. 다시 시도해 주세요.",
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.validationRed,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (notifications.isEmpty()) {
                HomeNotificationEmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                HomeNotificationList(
                    notifications = notifications,
                    onNotificationClick = onNotificationClick,
                    onLoadMoreNotifications = onLoadMoreNotifications,
                    hasNext = uiState.hasNext,
                    isLoadingMore = uiState.isLoadingMore,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
            }
        }

        if (isMenuVisible) {
            HomeNotificationMoreMenu(
                onMarkAllAsReadClick = {
                    onMarkAllAsReadClick()
                },
                isMarkingAllAsRead = uiState.isMarkingAllAsRead,
                hasUnreadNotifications = uiState.unreadCount > 0,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 66.dp, end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationScreenPreview() {
    DduruTheme {
        HomeNotificationScreen(
            uiState = HomeNotificationUiState.preview,
            onBackClick = {},
            onFilterClick = {},
            onNotificationClick = {},
            onLoadMoreNotifications = {},
            onMarkAllAsReadClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationEmptyScreenPreview() {
    DduruTheme {
        HomeNotificationScreen(
            uiState = HomeNotificationUiState(),
            onBackClick = {},
            onFilterClick = {},
            onNotificationClick = {},
            onLoadMoreNotifications = {},
            onMarkAllAsReadClick = {}
        )
    }
}
