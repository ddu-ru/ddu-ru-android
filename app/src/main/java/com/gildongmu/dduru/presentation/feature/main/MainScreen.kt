package com.gildongmu.dduru.presentation.feature.main

import BottomNavBar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.R
import com.gildongmu.dduru.presentation.navigation.BottomNavItem
import com.gildongmu.dduru.presentation.navigation.BottomNavRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@Composable
fun MainScreen(
    appState: MainAppState = rememberMainAppState()
) {
    val bottomNavItems = remember {
        listOf(
            BottomNavItem(BottomNavRoute.Home, "홈", R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.Matching, "매칭", R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.MyPage, "나의 여정", R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.Chat, "채팅",R.drawable.ic_bottom_nav_main),
            BottomNavItem(BottomNavRoute.Board, "커뮤니티", R.drawable.ic_bottom_nav_main)
        )
    }

    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize()
    ) {
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState),
            backStack = appState.bottomNavBackStack,
            entryProvider = { route ->
                when (route) {
                    is BottomNavRoute.Home -> NavEntry(route) { Text("메인")}
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
            currentRoute = appState.currentRoute,
            onItemClick = appState::navigate,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
                .windowInsetsPadding(
                    WindowInsets
                        .safeDrawing
                        .only(WindowInsetsSides.Bottom)

                )
                .hazeChild(
                    state = hazeState,
                    shape = RoundedCornerShape(99.dp),
                    style = HazeStyle(
                        tint = Color.White.copy(.5f),
                        blurRadius = 15.dp
                    )
                )
                .border(
                    width = Dp.Hairline,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = .8f),
                            Color.White.copy(alpha = .2f),
                        ),
                    ),
                    shape = RoundedCornerShape(99.dp)
                )
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() { MainScreen() }
