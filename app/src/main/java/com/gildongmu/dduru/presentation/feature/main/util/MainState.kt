package com.gildongmu.dduru.presentation.feature.main.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.gildongmu.dduru.presentation.navigation.BottomNavRoute

@Composable
fun rememberMainAppState(
    bottomNavBackStack: NavBackStack<NavKey> = rememberNavBackStack(BottomNavRoute.Home)
): MainState {
    return remember(bottomNavBackStack) {
        MainState(bottomNavBackStack)
    }
}

class MainState(
    val bottomNavBackStack: NavBackStack<NavKey>
) {
    val currentRoute: BottomNavRoute?
        get() = bottomNavBackStack.lastOrNull() as? BottomNavRoute

    fun navigate(route: BottomNavRoute) {
        if (currentRoute != route) {
            bottomNavBackStack.clear()
            bottomNavBackStack.add(route)
        }
    }
}
