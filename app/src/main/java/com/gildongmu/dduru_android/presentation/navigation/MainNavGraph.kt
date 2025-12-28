package com.gildongmu.dduru_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
            @Composable fun HomeScreen(){}
        }
        composable(BottomNavItem.Itinerary.route) {
            @Composable fun ItineraryScreen(){}
        }
        composable(BottomNavItem.Match.route) {
            @Composable fun MatchScreen(){}
        }
        composable(BottomNavItem.Chat.route) {
            @Composable fun ChatScreen(){}
        }
        composable(BottomNavItem.Mypage.route) {
            @Composable fun MyPageScreen(){}
        }
    }
}