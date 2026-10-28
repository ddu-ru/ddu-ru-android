package com.gildongmu.dduru.presentation.feature.main.screen

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatMainRoute
import com.gildongmu.dduru.presentation.feature.home.HomeRoute
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyDetailFilterType
import com.gildongmu.dduru.presentation.feature.journey.screen.JourneyRoute
import com.gildongmu.dduru.presentation.feature.matching.screen.MatchingRoute
import com.gildongmu.dduru.presentation.feature.mypage.screen.MyPageRoute
import com.gildongmu.dduru.presentation.navigation.BottomNavBar
import com.gildongmu.dduru.presentation.navigation.BottomNavItem
import com.gildongmu.dduru.presentation.navigation.BottomNavRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun MainScreen(
    onNavigateToHomeNotification: () -> Unit,
    onNavigateToHomeMenu: () -> Unit,
    onNavigateToChatRoom: (Long) -> Unit,
    onNavigateToMatchingCreatePost: () -> Unit,
    onNavigateToMatchingDetail: (Long) -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToTravelStyleSurvey: () -> Unit,
    onNavigateToJourneyDetail: (Long, String?) -> Unit,
    initialRoute: BottomNavRoute = BottomNavRoute.Home
) {
    val bottomNavItems = remember {
        listOf(
            BottomNavItem(BottomNavRoute.Home, "홈", R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.Matching, "매칭", R.drawable.ic_bottom_nav_matching),
            BottomNavItem(BottomNavRoute.Journey, "나의 여정", R.drawable.ic_bottom_nav_mypage),
            BottomNavItem(BottomNavRoute.Chat, "채팅", R.drawable.ic_bottom_nav_chat),
            BottomNavItem(BottomNavRoute.MyPage, "마이페이지", R.drawable.ic_bottom_nav_board)
        )
    }

    val bottomNavBackStack = rememberNavBackStack(initialRoute)
    val currentRoute = bottomNavBackStack.lastOrNull() as? BottomNavRoute
        ?: BottomNavRoute.Home
    val hazeState = remember { HazeState() }
    val navigateToBottomRoute: (BottomNavRoute) -> Unit = { route ->
        if (currentRoute != route) {
            bottomNavBackStack.clear()
            bottomNavBackStack.add(route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState),
            backStack = bottomNavBackStack,
            transitionSpec = {
                ContentTransform(EnterTransition.None, ExitTransition.None)
            },
            popTransitionSpec = {
                ContentTransform(EnterTransition.None, ExitTransition.None)
            },
            entryProvider = { route ->
                when (route) {
                    is BottomNavRoute.Home -> NavEntry(route) {
                        HomeRoute(
                            navigateToNotification = onNavigateToHomeNotification,
                            navigateToMenu = onNavigateToHomeMenu,
                            navigateToMatching = {
                                navigateToBottomRoute(BottomNavRoute.Matching)
                            },
                            navigateToUpcomingTripTask = {
                                navigateToBottomRoute(BottomNavRoute.Journey)
                            },
                            navigateToDestination = {
                                navigateToBottomRoute(BottomNavRoute.Matching)
                            },
                            navigateToMatchingDetail = onNavigateToMatchingDetail,
                            navigateToCreateTravelRoom = onNavigateToMatchingCreatePost
                        )
                    }
                    is BottomNavRoute.Matching -> NavEntry(route) {
                        MatchingRoute(
                            navigateToNotification = onNavigateToHomeNotification,
                            navigateToMenu = onNavigateToHomeMenu,
                            navigateToCreatePost = onNavigateToMatchingCreatePost,
                            navigateToPostDetail = onNavigateToMatchingDetail
                        )
                    }
                    is BottomNavRoute.MyPage -> NavEntry(route) {
                        MyPageRoute(
                            onNotificationClick = onNavigateToHomeNotification,
                            onMenuClick = onNavigateToHomeMenu,
                            onEditProfileClick = onNavigateToProfileEdit,
                            onTravelStyleClick = onNavigateToTravelStyleSurvey,
                            onStartTravelStyleTestClick = onNavigateToTravelStyleSurvey
                        )
                    }
                    is BottomNavRoute.Chat -> NavEntry(route) {
                        ChatMainRoute(
                            onNavigateToChatRoom = onNavigateToChatRoom
                        )
                    }
                    is BottomNavRoute.Journey -> NavEntry(route) {
                        JourneyRoute(
                            onCreateJourneyClick = onNavigateToMatchingCreatePost,
                            onJourneyClick = { journeyId -> onNavigateToJourneyDetail(journeyId, null) },
                            onScheduleAllClick = { journeyId ->
                                onNavigateToJourneyDetail(journeyId, JourneyDetailFilterType.SCHEDULE.name)
                            },
                            onNewsAllClick = { journeyId ->
                                onNavigateToJourneyDetail(journeyId, JourneyDetailFilterType.BOARD.name)
                            }
                        )
                    }
                    else -> NavEntry(route) { Text("잘못된 접근입니다.") }
                }
            }
        )

        BottomNavBar(
            items = bottomNavItems,
            currentRoute = currentRoute,
            onItemClick = { route ->
                navigateToBottomRoute(route)
            },
            hazeState = hazeState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    DduruTheme {
        MainScreen(
            onNavigateToHomeNotification = {},
            onNavigateToHomeMenu = {},
            onNavigateToChatRoom = {},
            onNavigateToMatchingCreatePost = {},
            onNavigateToProfileEdit = {},
            onNavigateToMatchingDetail = {},
            onNavigateToTravelStyleSurvey = {}
            onNavigateToMatchingDetail = {},
            onNavigateToJourneyDetail = { _, _ -> }
        )
    }
}
