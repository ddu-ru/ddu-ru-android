package com.gildongmu.dduru.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatImageViewerScreen
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatRoomMenuRoute
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatRoomRoute
import com.gildongmu.dduru.presentation.feature.home.screen.HomeMenuRoute
import com.gildongmu.dduru.presentation.feature.home.screen.HomeNotificationRoute
import com.gildongmu.dduru.presentation.feature.login.screen.LoginRoute
import com.gildongmu.dduru.presentation.feature.login.screen.WelcomeScreen
import com.gildongmu.dduru.presentation.feature.main.screen.MainScreen
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostRoute
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingEditPostRoute
import com.gildongmu.dduru.presentation.feature.matching.post.screen.MatchingDetailRoute
import com.gildongmu.dduru.presentation.feature.poll.screen.PollCreateRoute
import com.gildongmu.dduru.presentation.feature.register.profile.screen.ProfileSettingRoute
import com.gildongmu.dduru.presentation.feature.register.screen.SignupRoute
import com.gildongmu.dduru.presentation.feature.register.survey.SurveyMainScreen

@Composable
fun RootNavigation(
    pendingNotificationRoute: RootNavRoute? = null,
    onNotificationRouteConsumed: () -> Unit = {},
    rootViewModel: RootViewModel = hiltViewModel()
) {
    val startDestination by rootViewModel.startDestination.collectAsStateWithLifecycle()

    if (startDestination == null) {
        DduruLoadingScreen()
    } else {
        val rootBackStack = rememberNavBackStack(startDestination!!)
        LaunchedEffect(startDestination) {
            // 앱 재설치/프로세스 복원 시 이전 Main back stack이 로그인 시작 화면을 덮지 않게 한다.
            rootBackStack.clear()
            rootBackStack.add(startDestination!!)
        }
        fun navigateBack() {
            if (rootBackStack.size > 1) {
                rootBackStack.removeLastOrNull()
            }
        }
        LaunchedEffect(pendingNotificationRoute) {
            val route = pendingNotificationRoute ?: return@LaunchedEffect
            if (startDestination !is RootNavRoute.Login) {
                rootBackStack.clear()
                when (route) {
                    is RootNavRoute.Main,
                    is RootNavRoute.MainTab -> rootBackStack.add(route)
                    else -> {
                        rootBackStack.add(RootNavRoute.Main)
                        rootBackStack.add(route)
                    }
                }
            }
            onNotificationRouteConsumed()
        }

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
                    is RootNavRoute.Login -> NavEntry(route) {
                        LoginRoute(
                            onLoginSuccess = { isNewUser ->
                                if (isNewUser) {
                                    rootBackStack.clear()
                                    rootBackStack.add(RootNavRoute.Welcome)
                                } else {
                                    rootBackStack.clear()
                                    rootBackStack.add(RootNavRoute.Main)
                                }
                            },
                            onGuestClick = {
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.Main)
                            }
                        )
                    }

                    is RootNavRoute.Welcome -> NavEntry(route) {
                        WelcomeScreen(
                            onNextClick = {
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.Register)
                            }
                        )
                    }

                    is RootNavRoute.Register -> NavEntry(route) {
                        SignupRoute(
                            onSignupComplete = {
                                rootBackStack.add(RootNavRoute.RegisterSurvey)
                            }
                        )
                    }

                    is RootNavRoute.RegisterSurvey -> NavEntry(route) {
                        SurveyMainScreen(
                            onSurveyComplete = {
                                rootBackStack.add(RootNavRoute.RegisterProfile)
                            },
                            onBackPressed = { navigateBack() }
                        )
                    }

                    is RootNavRoute.RegisterProfile -> NavEntry(route) {
                        ProfileSettingRoute(
                            onProfileComplete = {
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.Main)
                            }
                        )
                    }

                    is RootNavRoute.Main -> NavEntry(route) {
                        MainScreen(
                            onNavigateToHomeNotification = {
                                rootBackStack.add(RootNavRoute.HomeNotification)
                            },
                            onNavigateToHomeMenu = {
                                rootBackStack.add(RootNavRoute.HomeMenu)
                            },
                            onNavigateToChatRoom = { chatRoomId ->
                                rootBackStack.add(RootNavRoute.ChatRoom(chatRoomId))
                            },
                            onNavigateToMatchingCreatePost = {
                                rootBackStack.add(RootNavRoute.MatchingCreatePost)
                            },
                            onNavigateToMatchingDetail = { postId ->
                                rootBackStack.add(RootNavRoute.MatchingDetail(postId))
                            }
                        )
                    }

                    is RootNavRoute.MainTab -> NavEntry(route) {
                        MainScreen(
                            onNavigateToHomeNotification = {
                                rootBackStack.add(RootNavRoute.HomeNotification)
                            },
                            onNavigateToHomeMenu = {
                                rootBackStack.add(RootNavRoute.HomeMenu)
                            },
                            onNavigateToChatRoom = { chatRoomId ->
                                rootBackStack.add(RootNavRoute.ChatRoom(chatRoomId))
                            },
                            onNavigateToMatchingCreatePost = {
                                rootBackStack.add(RootNavRoute.MatchingCreatePost)
                            },
                            onNavigateToMatchingDetail = { postId ->
                                rootBackStack.add(RootNavRoute.MatchingDetail(postId))
                            },
                            initialRoute = route.bottomNavRoute
                        )
                    }

                    is RootNavRoute.HomeNotification -> NavEntry(route) {
                        HomeNotificationRoute(
                            onBackClick = { navigateBack() },
                            onNavigateToResource = { destination ->
                                rootBackStack.add(destination)
                            }
                        )
                    }

                    is RootNavRoute.HomeMenu -> NavEntry(route) {
                        HomeMenuRoute(
                            onBackClick = { navigateBack() }
                        )
                    }

                    is RootNavRoute.ChatRoom -> NavEntry(route) {
                        ChatRoomRoute(
                            chatRoomId = route.chatRoomId,
                            onBackClick = { navigateBack() },
                            onNavigateToMenu = { chatRoomId ->
                                rootBackStack.add(RootNavRoute.ChatRoomMenu(chatRoomId))
                            },
                            onNavigateToPollCreate = {
                                rootBackStack.add(RootNavRoute.PollCreate)
                            },
                            onNavigateToImageViewer = { imageUrls, initialIndex ->
                                rootBackStack.add(RootNavRoute.ChatImageViewer(imageUrls, initialIndex))
                            }
                        )
                    }

                    is RootNavRoute.ChatRoomMenu -> NavEntry(route) {
                        ChatRoomMenuRoute(
                            chatRoomId = route.chatRoomId,
                            onBackClick = { navigateBack() }
                        )
                    }

                    is RootNavRoute.ChatImageViewer -> NavEntry(route) {
                        ChatImageViewerScreen(
                            imageUrls = route.imageUrls,
                            initialIndex = route.initialIndex,
                            onCloseClick = { navigateBack() }
                        )
                    }

                    is RootNavRoute.PollCreate -> NavEntry(route) {
                        PollCreateRoute(
                            onBackClick = { navigateBack() }
                        )
                    }

                    is RootNavRoute.MatchingCreatePost -> NavEntry(route) {
                        MatchingCreatePostRoute(
                            onClose = { navigateBack() },
                            onComplete = { navigateBack() }
                        )
                    }

                    is RootNavRoute.MatchingDetail -> NavEntry(route) {
                        MatchingDetailRoute(
                            postId = route.postId,
                            onBackClick = { navigateBack() },
                            onOverflowClick = {
                                rootBackStack.add(RootNavRoute.MatchingEditPost(route.postId))
                            },
                            onShareClick = {},
                            onProfileClick = {},
                            onContactClick = {},
                            onBottomActionClick = {}
                        )
                    }

                    is RootNavRoute.MatchingEditPost -> NavEntry(route) {
                        MatchingEditPostRoute(
                            postId = route.postId,
                            onBackClick = { navigateBack() },
                            onSaveClick = { navigateBack() }
                        )
                    }

                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
