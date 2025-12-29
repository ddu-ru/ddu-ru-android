package com.gildongmu.dduru_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainNavGraph(
    navController: NavHostController,
    startDestination: String = BottomNavItem.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(BottomNavItem.Home.route) {
            HomeDummyScreen()
        }
        composable(BottomNavItem.Itinerary.route) {
            ItineraryDummyScreen()
        }
        composable(BottomNavItem.Match.route) {
            MatchDummyScreen()
        }
        composable(BottomNavItem.Chat.route) {
            ChatDummyScreen()
        }
        composable(BottomNavItem.Mypage.route) {
            MyPageDummyScreen()
        }
    }
}

@Composable
private fun HomeDummyScreen() {
    CenterText("Home")
}

@Composable
private fun ItineraryDummyScreen() {
    CenterText("Itinerary")
}

@Composable
private fun MatchDummyScreen() {
    CenterText("Match")
}

@Composable
private fun ChatDummyScreen() {
    CenterText("Chat")
}

@Composable
private fun MyPageDummyScreen() {
    CenterText("MyPage")
}

@Composable
private fun CenterText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}
