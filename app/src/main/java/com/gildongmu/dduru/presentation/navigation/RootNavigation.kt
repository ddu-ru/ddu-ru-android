package com.gildongmu.dduru.presentation.navigation

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
import com.gildongmu.dduru.presentation.feature.login.LoginBinder
import com.gildongmu.dduru.presentation.feature.login.WelcomeScreen

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
            entryProvider = { route ->
                when (route) {
                    is ScreenRoute.LoginRoute -> NavEntry(route) {
                        LoginBinder(
                            onLoginSuccess = { isNewUser ->
                                if (isNewUser) {
                                    rootBackStack.clear()
                                    rootBackStack.add(ScreenRoute.WelcomeRoute)
                                } else {
                                    rootBackStack.clear()
                                    rootBackStack.add(ScreenRoute.MainRoute)
                                }
                            },
                            onGuestClick = {
                                rootBackStack.clear()
                                rootBackStack.add(ScreenRoute.MainRoute)
                            }
                        )
                    }

                    is ScreenRoute.WelcomeRoute -> NavEntry(route) {
                        WelcomeScreen(
                            onNextClick = {
                                rootBackStack.clear()
                                rootBackStack.add(ScreenRoute.RegisterRoute)
                            }
                        )
                    }

                    is ScreenRoute.RegisterRoute -> NavEntry(route) {}
                    is ScreenRoute.MainRoute -> NavEntry(route) {}
                    is ScreenRoute.MatchingRoute -> NavEntry(route) {}
                    is ScreenRoute.MyPageRoute -> NavEntry(route) {}
                    is ScreenRoute.ChatRoute -> NavEntry(route) {}
                    is ScreenRoute.BoardRoute -> NavEntry(route) {}
                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
