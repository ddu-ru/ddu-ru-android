package com.gildongmu.dduru.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface ScreenRoute : NavKey {

    @Serializable data object LoginRoute : ScreenRoute
    @Serializable data object WelcomeRoute : ScreenRoute
    @Serializable data object RegisterRoute : ScreenRoute
    @Serializable data object MainRoute : ScreenRoute
    @Serializable data object MatchingRoute : ScreenRoute
    @Serializable data object MyPageRoute : ScreenRoute
    @Serializable data object ChatRoute : ScreenRoute
    @Serializable data object BoardRoute : ScreenRoute

}
