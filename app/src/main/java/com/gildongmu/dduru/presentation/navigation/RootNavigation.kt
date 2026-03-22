package com.gildongmu.dduru.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.presentation.feature.login.screen.WelcomeScreen
import com.gildongmu.dduru.presentation.feature.login.screen.LoginRoute
import com.gildongmu.dduru.presentation.feature.main.MainScreen

@Composable
fun RootNavigation(
    rootViewModel: RootViewModel = hiltViewModel()
) {
    val startDestination by rootViewModel.startDestination.collectAsStateWithLifecycle()

    if (startDestination == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val rootBackStack = rememberNavBackStack(startDestination!!)

        NavDisplay(
            backStack = rootBackStack,
            transitionSpec = {
                ContentTransform(EnterTransition.None, ExitTransition.None)
            },
            popTransitionSpec = {
                ContentTransform(EnterTransition.None, ExitTransition.None)
            },
            entryProvider = { route ->
                when (route) {
                    is RootNavRoute.LoginNavRoute -> NavEntry(route) {
                        LoginRoute(
                            onLoginSuccess = { isNewUser ->
                                if (isNewUser) {
                                    rootBackStack.clear()
                                    rootBackStack.add(RootNavRoute.WelcomeNavRoute)
                                } else {
                                    rootBackStack.clear()
                                    rootBackStack.add(RootNavRoute.MainNavRoute)
                                }
                            },
                            onGuestClick = {
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.MainNavRoute)
                            }

                        )
                    }

                    is RootNavRoute.WelcomeNavRoute -> NavEntry(route) {
                        WelcomeScreen(
                            onNextClick = {
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.RegisterNavRoute)
                            }
                        )
                    }

                    is RootNavRoute.RegisterNavRoute -> NavEntry(route) {}

                    is RootNavRoute.MainNavRoute -> NavEntry(route) {
                        MainScreen()
                    }

                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
