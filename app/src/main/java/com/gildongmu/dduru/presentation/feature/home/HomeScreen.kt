package com.gildongmu.dduru.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.gildongmu.dduru.presentation.feature.home.component.AiMatchingSection
import com.gildongmu.dduru.presentation.feature.home.component.HomeHeader
import com.gildongmu.dduru.presentation.feature.home.component.HomeRealtimePopularDestinationSection
import com.gildongmu.dduru.presentation.feature.home.component.HomeSuperHostSection
import com.gildongmu.dduru.presentation.feature.home.component.TravelCompanionSection
import com.gildongmu.dduru.presentation.feature.home.component.UpcomingTripCard
import com.gildongmu.dduru.presentation.feature.home.component.UpcomingTripEmptyState
import com.gildongmu.dduru.presentation.feature.home.component.WritingButton
import com.gildongmu.dduru.presentation.feature.home.component.WritingExpandedMenu
import com.gildongmu.dduru.presentation.feature.home.effect.HomeSideEffect
import com.gildongmu.dduru.presentation.feature.home.state.HomeUiState
import com.gildongmu.dduru.presentation.feature.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    navigateToNotification: () -> Unit = {},
    navigateToMenu: () -> Unit = {},
    navigateToUpcomingTripTask: () -> Unit = {},
    navigateToDestination: (Long) -> Unit = {},
    navigateToSuperHost: (Long) -> Unit = {},
    navigateToCompanion: (Long) -> Unit = {},
    navigateToCreateTravelRoom: () -> Unit = {},
    navigateToWriteCommunity: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                HomeSideEffect.NavigateToNotification -> navigateToNotification()
                HomeSideEffect.NavigateToMenu -> navigateToMenu()
                HomeSideEffect.NavigateToUpcomingTripTask -> navigateToUpcomingTripTask()
                is HomeSideEffect.NavigateToDestination -> navigateToDestination(sideEffect.id)
                is HomeSideEffect.NavigateToSuperHost -> navigateToSuperHost(sideEffect.id)
                is HomeSideEffect.NavigateToCompanion -> navigateToCompanion(sideEffect.id)
                HomeSideEffect.NavigateToCreateTravelRoom -> navigateToCreateTravelRoom()
                HomeSideEffect.NavigateToWriteCommunity -> navigateToWriteCommunity()
            }
        }
    }

    when (val state = uiState) {
        UiState.Empty -> Unit

        UiState.Loading -> DduruLoadingScreen()

        UiState.Failure -> DduruFailureScreen(message = "홈 정보를 불러오지 못했어요.")

        is UiState.Success -> {
            HomeScreen(
                uiState = state.data,
                paddingValues = paddingValues,
                onNotificationClick = viewModel::onNotificationClick,
                onMenuClick = viewModel::onMenuClick,
                onUpcomingTripTaskClick = viewModel::onUpcomingTripTaskClick,
                onRefreshPopularDestinationClick = viewModel::onRefreshPopularDestinationClick,
                onPopularDestinationClick = viewModel::onPopularDestinationClick,
                onAiMatchingPassClick = viewModel::onAiMatchingPassClick,
                onAiMatchingAcceptClick = viewModel::onAiMatchingAcceptClick,
                onSuperHostMoreClick = viewModel::onSuperHostMoreClick,
                onSuperHostClick = viewModel::onSuperHostClick,
                onSuperHostLikeClick = viewModel::onSuperHostLikeClick,
                onCompanionMoreClick = viewModel::onCompanionMoreClick,
                onCompanionClick = viewModel::onCompanionClick,
                onCreateTravelRoomClick = viewModel::onCreateTravelRoomClick,
                onWriteCommunityClick = viewModel::onWriteCommunityClick
            )
        }
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    paddingValues: PaddingValues,
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onUpcomingTripTaskClick: () -> Unit,
    onRefreshPopularDestinationClick: () -> Unit,
    onPopularDestinationClick: (Long) -> Unit,
    onAiMatchingPassClick: () -> Unit,
    onAiMatchingAcceptClick: () -> Unit,
    onSuperHostMoreClick: () -> Unit,
    onSuperHostClick: (Long) -> Unit,
    onSuperHostLikeClick: (Long) -> Unit,
    onCompanionMoreClick: () -> Unit,
    onCompanionClick: (Long) -> Unit,
    onCreateTravelRoomClick: () -> Unit,
    onWriteCommunityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isWritingMenuVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            HomeHeader(
                onNotificationClick = onNotificationClick,
                onMenuClick = onMenuClick
            )

            Spacer(modifier = Modifier.height(34.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 110.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                item {
                    if (uiState.upcomingTrip.hasUpcomingTrip) {
                        UpcomingTripCard(
                            title = uiState.upcomingTrip.title,
                            dateText = uiState.upcomingTrip.dateText,
                            currentMember = uiState.upcomingTrip.currentMember,
                            maxMember = uiState.upcomingTrip.maxMember,
                            dDay = uiState.upcomingTrip.dDay,
                            taskCount = uiState.upcomingTrip.taskCount,
                            onUpcomingTripTaskClick = onUpcomingTripTaskClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        UpcomingTripEmptyState(modifier = Modifier.fillMaxWidth())
                    }
                }

                item {
                    HomeRealtimePopularDestinationSection(
                        items = uiState.popularDestinations,
                        updatedText = uiState.popularDestinationUpdatedText,
                        onRefreshClick = onRefreshPopularDestinationClick,
                        onItemClick = { onPopularDestinationClick(it.id) }
                    )
                }

                item {
                    AiMatchingSection(
                        matchUsedCount = uiState.aiMatchingUsedCount,
                        data = uiState.aiMatchingCard,
                        onPass = onAiMatchingPassClick,
                        onAccept = onAiMatchingAcceptClick
                    )
                }

                item {
                    HomeSuperHostSection(
                        items = uiState.superHosts,
                        onMoreClick = onSuperHostMoreClick,
                        onCardClick = onSuperHostClick,
                        onLikeClick = onSuperHostLikeClick
                    )
                }

                item {
                    TravelCompanionSection(
                        items = uiState.sameDestinationCompanions,
                        title = "선호하는 여행지가 같은 동행",
                        onMoreClick = onCompanionMoreClick,
                        onCardClick = { onCompanionClick(it.id) }
                    )
                }

                item {
                    TravelCompanionSection(
                        items = uiState.sameAgeCompanions,
                        title = "나이대가 같은 동행",
                        onMoreClick = onCompanionMoreClick,
                        onCardClick = { onCompanionClick(it.id) }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(end = 0.dp)
        ) {
            if (isWritingMenuVisible) {
                WritingExpandedMenu(
                    onDismiss = { isWritingMenuVisible = false },
                    onCreateTravelRoom = {
                        isWritingMenuVisible = false
                        onCreateTravelRoomClick()
                    },
                    onWriteCommunity = {
                        isWritingMenuVisible = false
                        onWriteCommunityClick()
                    }
                )
            } else {
                WritingButton(
                    onClick = { isWritingMenuVisible = true }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F6F8)
@Composable
private fun HomeScreenPreview() {
    DduruTheme {
        HomeScreen(
            uiState = HomeUiState.fake,
            paddingValues = PaddingValues(0.dp),
            onNotificationClick = {},
            onMenuClick = {},
            onUpcomingTripTaskClick = {},
            onRefreshPopularDestinationClick = {},
            onPopularDestinationClick = {},
            onAiMatchingPassClick = {},
            onAiMatchingAcceptClick = {},
            onSuperHostMoreClick = {},
            onSuperHostClick = {},
            onSuperHostLikeClick = {},
            onCompanionMoreClick = {},
            onCompanionClick = {},
            onCreateTravelRoomClick = {},
            onWriteCommunityClick = {}
        )
    }
}
