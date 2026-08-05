package com.gildongmu.dduru.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatRoomRoute
import com.gildongmu.dduru.presentation.feature.home.screen.HomeMenuRoute
import com.gildongmu.dduru.presentation.feature.home.screen.HomeNotificationRoute
import com.gildongmu.dduru.presentation.feature.login.screen.LoginRoute
import com.gildongmu.dduru.presentation.feature.login.screen.WelcomeScreen
import com.gildongmu.dduru.presentation.feature.main.screen.MainScreen
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostRoute
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingEditPostRoute
import com.gildongmu.dduru.presentation.feature.matching.post.screen.MatchingDetailRoute
import com.gildongmu.dduru.presentation.feature.register.profile.screen.ProfileSettingRoute
import com.gildongmu.dduru.presentation.feature.register.screen.SignupRoute
import com.gildongmu.dduru.presentation.feature.register.survey.SurveyMainScreen

@Composable
fun RootNavigation(
    rootViewModel: RootViewModel = hiltViewModel()
) {
    val startDestination by rootViewModel.startDestination.collectAsStateWithLifecycle()

    if (startDestination == null) {
        DduruLoadingScreen()
    } else {
        val rootBackStack = rememberNavBackStack(startDestination!!)
        fun navigateBack() {
            if (rootBackStack.size > 1) {
                rootBackStack.removeLastOrNull()
            }
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
                                rootBackStack.clear()
                                rootBackStack.add(RootNavRoute.RegisterSurvey)
                            }
                        )
                    }

                    is RootNavRoute.RegisterSurvey -> NavEntry(route) {
                        SurveyMainScreen(
                            onSurveyComplete = {
                                rootBackStack.clear()
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

                    is RootNavRoute.HomeNotification -> NavEntry(route) {
                        HomeNotificationRoute(
                            onBackClick = { navigateBack() }
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
