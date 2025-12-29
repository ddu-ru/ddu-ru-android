package com.gildongmu.dduru_android.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainRoute(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            startDestination = BottomNavItem.Home.route
        )
    }
}
