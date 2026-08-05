package com.gildongmu.dduru.presentation.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.chat.screen.ChatRoomRoute
import com.gildongmu.dduru.presentation.feature.login.screen.LoginRoute
import com.gildongmu.dduru.presentation.feature.login.screen.WelcomeScreen
import com.gildongmu.dduru.presentation.feature.main.screen.MainScreen
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingCreatePostRoute
import com.gildongmu.dduru.presentation.feature.matching.createPost.screen.MatchingEditPostRoute
import com.gildongmu.dduru.presentation.feature.matching.post.screen.MatchingDetailScreen
import com.gildongmu.dduru.presentation.feature.matching.post.uistate.MatchingDetailUiState
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
                            onBackPressed = { rootBackStack.removeLastOrNull() }
                        )
                    }

                    is RootNavRoute.RegisterProfile -> NavEntry(route) {
                        ProfileSettingRoute()
                    }

                    is RootNavRoute.Main -> NavEntry(route) {
                        MainScreen(
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

                    is RootNavRoute.ChatRoom -> NavEntry(route) {
                        ChatRoomRoute(
                            chatRoomId = route.chatRoomId,
                            onBackClick = { rootBackStack.removeLastOrNull() }
                        )
                    }

                    is RootNavRoute.MatchingCreatePost -> NavEntry(route) {
                        MatchingCreatePostRoute(
                            onClose = { rootBackStack.removeLastOrNull() },
                            onComplete = { rootBackStack.removeLastOrNull() }
                        )
                    }

                    is RootNavRoute.MatchingDetail -> NavEntry(route) {
                        MatchingDetailScreen(
                            uiState = MatchingDetailUiState(id = route.postId),
                            onBackClick = { rootBackStack.removeLastOrNull() },
                            onLikeClick = {},
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
                            onBackClick = { rootBackStack.removeLastOrNull() },
                            onSaveClick = { rootBackStack.removeLastOrNull() }
                        )
                    }

                    else -> NavEntry(route) {}
                }
            }
        )
    }
}
