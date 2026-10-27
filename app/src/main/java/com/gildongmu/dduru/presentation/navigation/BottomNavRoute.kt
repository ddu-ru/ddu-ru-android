package com.gildongmu.dduru.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface BottomNavRoute : NavKey {
    @Serializable data object Home : BottomNavRoute

    @Serializable data object Matching : BottomNavRoute

    @Serializable data object Journey : BottomNavRoute

    @Serializable data object Chat : BottomNavRoute

    @Serializable data object MyPage : BottomNavRoute
}
