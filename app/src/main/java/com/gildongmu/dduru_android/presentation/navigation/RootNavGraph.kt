package com.gildongmu.dduru_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru_android.presentation.feature.login.LoginViewModel
import com.gildongmu.dduru_android.presentation.feature.main.Screen

private object RootRoute {
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val MAIN = "main"
}

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val startScreen by viewModel.startScreen.collectAsStateWithLifecycle()
    val startDestination = when (startScreen) {
        Screen.Home -> RootRoute.MAIN
        else -> RootRoute.LOGIN
    }

    if (startScreen == Screen.Splash) {
        SplashDummyScreen()
    } else {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(RootRoute.LOGIN) {
                LoginRoute(
                    onLoginSuccess = { isNewUser ->
                        val target = if (isNewUser) RootRoute.ONBOARDING else RootRoute.MAIN
                        navController.navigate(target) {
                            popUpTo(RootRoute.LOGIN) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onGuestClick = {
                        navController.navigate(RootRoute.MAIN) {
                            popUpTo(RootRoute.LOGIN) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(RootRoute.ONBOARDING) {
                OnboardingDummyScreen(
                    onFinish = {
                        navController.navigate(RootRoute.MAIN) {
                            popUpTo(RootRoute.ONBOARDING) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(RootRoute.MAIN) {
                MainRoute()
            }
        }
    }
}

@Composable
private fun OnboardingDummyScreen(
    onFinish: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onFinish) {
            Text(text = "온보딩 완료 (더미)")
        }
    }
}

@Composable
private fun SplashDummyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "DDURU 로딩 중...")
    }
}
