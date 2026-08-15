package com.gildongmu.dduru.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface RootNavRoute : NavKey {
    @Serializable data object Login : RootNavRoute

    @Serializable data object Welcome : RootNavRoute

    @Serializable data object Register : RootNavRoute

    @Serializable data object RegisterSurvey : RootNavRoute

    @Serializable data object RegisterProfile : RootNavRoute

    @Serializable data object Main : RootNavRoute

    @Serializable data object HomeNotification : RootNavRoute

    @Serializable data object HomeMenu : RootNavRoute

    @Serializable data class ChatRoom(val chatRoomId: Long) : RootNavRoute

    @Serializable data object MatchingCreatePost : RootNavRoute

    @Serializable data class MatchingDetail(val postId: Long) : RootNavRoute

    @Serializable data class MatchingEditPost(val postId: Long) : RootNavRoute
}
