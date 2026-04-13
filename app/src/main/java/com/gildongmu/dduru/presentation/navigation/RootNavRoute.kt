package com.gildongmu.dduru.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface RootNavRoute : NavKey {
    @Serializable data object Login : RootNavRoute

    @Serializable data object Welcome : RootNavRoute

    @Serializable data object Register : RootNavRoute

    @Serializable data object Main : RootNavRoute
}
