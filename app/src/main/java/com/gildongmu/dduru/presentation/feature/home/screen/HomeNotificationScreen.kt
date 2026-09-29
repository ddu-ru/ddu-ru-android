package com.gildongmu.dduru.presentation.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
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
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationFilter
import com.gildongmu.dduru.presentation.feature.home.state.HomeNotificationUiState
import com.gildongmu.dduru.presentation.feature.home.viewmodel.HomeNotificationViewModel

@Composable
fun HomeNotificationRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeNotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            onMarkAllAsReadClick = viewModel::onMarkAllAsReadClick,
            onDeleteAllClick = viewModel::onDeleteAllClick,
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
    onMarkAllAsReadClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
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

            if (notifications.isEmpty()) {
                HomeNotificationEmptyState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                HomeNotificationList(
                    notifications = notifications,
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
                    isMenuVisible = false
                    onMarkAllAsReadClick()
                },
                onDeleteAllClick = {
                    isMenuVisible = false
                    onDeleteAllClick()
                },
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
            onMarkAllAsReadClick = {},
            onDeleteAllClick = {}
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
            onMarkAllAsReadClick = {},
            onDeleteAllClick = {}
        )
    }
}
