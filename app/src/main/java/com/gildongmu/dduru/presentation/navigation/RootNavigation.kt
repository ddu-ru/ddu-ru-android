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
import com.gildongmu.dduru.presentation.feature.mypage.screen.ProfileEditPreferencesRoute
import com.gildongmu.dduru.presentation.feature.mypage.screen.ProfileEditRoute
import com.gildongmu.dduru.presentation.feature.mypage.screen.TravelDestinationSelectRoute
import com.gildongmu.dduru.presentation.feature.poll.screen.PollCreateRoute
import com.gildongmu.dduru.presentation.feature.register.profile.screen.ProfileSettingRoute
import com.gildongmu.dduru.presentation.feature.register.screen.SignupRoute
import com.gildongmu.dduru.presentation.feature.register.survey.SurveyMainScreen

private const val PROFILE_EDIT_INFO_TAB_INDEX = 0
private const val PROFILE_EDIT_STYLE_TAB_INDEX = 2

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
        fun navigateBack() {
            if (rootBackStack.size > 1) {
                rootBackStack.removeLastOrNull()
            }
        }
        fun replaceTop(route: RootNavRoute) {
            rootBackStack.removeLastOrNull()
            rootBackStack.add(route)
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
                            onNavigateToProfileEdit = {
                                rootBackStack.add(RootNavRoute.ProfileEdit)
                            },
                            onNavigateToMatchingDetail = { postId ->
                                rootBackStack.add(RootNavRoute.MatchingDetail(postId))
                            },
                            onNavigateToTravelStyleSurvey = {
                                rootBackStack.add(RootNavRoute.TravelStyleSurvey)
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
                            onNavigateToProfileEdit = {
                                rootBackStack.add(RootNavRoute.ProfileEdit)
                            },
                            onNavigateToMatchingDetail = { postId ->
                                rootBackStack.add(RootNavRoute.MatchingDetail(postId))
                            },
                            onNavigateToTravelStyleSurvey = {
                                rootBackStack.add(RootNavRoute.TravelStyleSurvey)
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

                    is RootNavRoute.ProfileEdit -> NavEntry(route) {
                        ProfileEditRoute(
                            onBackClick = { navigateBack() },
                            onNavigateToDestinationTab = {
                                replaceTop(RootNavRoute.ProfileEditPreferences)
                            },
                            onNavigateToStyleTab = {
                                rootBackStack.add(RootNavRoute.TravelStyleSurvey)
                            }
                        )
                    }

                    is RootNavRoute.ProfileEditPreferences -> NavEntry(route) {
                        ProfileEditPreferencesRoute(
                            onBackClick = { navigateBack() },
                            onNavigateToDestinationSelect = {
                                rootBackStack.add(RootNavRoute.TravelDestinationSelect)
                            },
                            onTabClick = { index ->
                                when (index) {
                                    PROFILE_EDIT_INFO_TAB_INDEX -> replaceTop(RootNavRoute.ProfileEdit)
                                    PROFILE_EDIT_STYLE_TAB_INDEX -> rootBackStack.add(RootNavRoute.TravelStyleSurvey)
                                }
                            }
                        )
                    }

                    is RootNavRoute.TravelDestinationSelect -> NavEntry(route) {
                        TravelDestinationSelectRoute(onClose = { navigateBack() })
                    }

                    is RootNavRoute.TravelStyleSurvey -> NavEntry(route) {
                        SurveyMainScreen(
                            onSurveyComplete = { navigateBack() },
                            onBackPressed = { navigateBack() }
                        )
                    }

                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
