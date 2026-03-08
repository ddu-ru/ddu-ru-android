package com.gildongmu.dduru.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface RootNavRoute : NavKey {
    @Serializable data object LoginNavRoute : RootNavRoute
    @Serializable data object WelcomeNavRoute : RootNavRoute
    @Serializable data object RegisterNavRoute : RootNavRoute
    @Serializable data object MainNavRoute : RootNavRoute
}
