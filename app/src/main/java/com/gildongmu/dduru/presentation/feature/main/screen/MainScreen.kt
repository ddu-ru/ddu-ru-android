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
import com.gildongmu.dduru.presentation.navigation.BottomNavBar
import com.gildongmu.dduru.presentation.navigation.BottomNavItem
import com.gildongmu.dduru.presentation.navigation.BottomNavRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun MainScreen() {
    val bottomNavItems = remember {
        listOf(
            BottomNavItem(BottomNavRoute.Home, "홈", R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.Matching, "매칭", R.drawable.ic_bottom_nav_matching),
            BottomNavItem(BottomNavRoute.MyPage, "나의 여정", R.drawable.ic_bottom_nav_mypage),
            BottomNavItem(BottomNavRoute.Chat, "채팅", R.drawable.ic_bottom_nav_chat),
            BottomNavItem(BottomNavRoute.Board, "커뮤니티", R.drawable.ic_bottom_nav_board)
        )
    }

    val bottomNavBackStack = rememberNavBackStack(BottomNavRoute.Home)
    val currentRoute = bottomNavBackStack.lastOrNull() as? BottomNavRoute
        ?: BottomNavRoute.Home
    val hazeState = remember { HazeState() }

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
                    is BottomNavRoute.Home -> NavEntry(route) { Text("홈") }
                    is BottomNavRoute.Matching -> NavEntry(route) { Text("매칭") }
                    is BottomNavRoute.Board -> NavEntry(route) { Text("게시판") }
                    is BottomNavRoute.Chat -> NavEntry(route) { Text("채팅") }
                    is BottomNavRoute.MyPage -> NavEntry(route) { Text("마이") }
                    else -> NavEntry(route) { Text("잘못된 접근입니다.") }
                }
            }
        )

        BottomNavBar(
            items = bottomNavItems,
            currentRoute = currentRoute,
            onItemClick = { route ->
                if (currentRoute != route) {
                    bottomNavBackStack.clear()
                    bottomNavBackStack.add(route)
                }
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
    MainScreen()
}
