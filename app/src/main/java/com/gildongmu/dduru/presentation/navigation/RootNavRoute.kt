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

    @Serializable data class MainTab(val bottomNavRoute: BottomNavRoute) : RootNavRoute

    @Serializable data object HomeNotification : RootNavRoute

    @Serializable data object HomeMenu : RootNavRoute

    @Serializable data class ChatRoom(val chatRoomId: Long) : RootNavRoute

    @Serializable data class ChatRoomMenu(val chatRoomId: Long) : RootNavRoute

    @Serializable data class ChatImageViewer(val imageUrls: List<String>, val initialIndex: Int) : RootNavRoute

    @Serializable data object PollCreate : RootNavRoute

    @Serializable data object MatchingCreatePost : RootNavRoute

    @Serializable data class MatchingDetail(val postId: Long) : RootNavRoute

    @Serializable data class MatchingEditPost(val postId: Long) : RootNavRoute

    @Serializable data object ProfileEditPreferences : RootNavRoute

    @Serializable data object TravelDestinationSelect : RootNavRoute

    @Serializable data object ProfileEdit : RootNavRoute

    @Serializable data object TravelStyleSurvey : RootNavRoute

    @Serializable data class JourneyDetail(
        val journeyId: Long,
        val initialFilter: String? = null
    ) : RootNavRoute

    @Serializable data class JourneyAdmin(val journeyId: Long) : RootNavRoute

    @Serializable data class JourneyPostDetail(val journeyId: Long, val postId: Long) : RootNavRoute

    @Serializable data class JourneyPostCompose(val journeyId: Long) : RootNavRoute

    @Serializable data class JourneyScheduleCompose(val journeyId: Long) : RootNavRoute

    @Serializable data class JourneyVoteCompose(val journeyId: Long) : RootNavRoute
}
