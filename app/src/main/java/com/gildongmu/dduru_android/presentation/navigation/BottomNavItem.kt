package com.gildongmu.dduru_android.presentation.navigation

import androidx.annotation.DrawableRes
import com.gildongmu.dduru_android.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
) {

    object Home : BottomNavItem(
        route = "home",
        label = "홈",
        iconRes = R.drawable.ic_bottom_navigation_tab_home
    )

    object Itinerary : BottomNavItem(
        route = "itinerary",
        label = "일정",
        iconRes = R.drawable.ic_bottom_navigation_tab_itinerary
    )

    object Match : BottomNavItem(
        route = "match",
        label = "매칭",
        iconRes = R.drawable.ic_bottom_navigation_tab_match
    )

    object Chat : BottomNavItem(
        route = "chat",
        label = "채팅",
        iconRes = R.drawable.ic_bottom_navigation_tab_chat
    )

    object Mypage : BottomNavItem(
        route = "myPage",
        label = "마이",
        iconRes = R.drawable.ic_bottom_navigation_tab_mypage
    )

    companion object {
        val items = listOf(
            Home,
            Itinerary,
            Match,
            Chat,
            Mypage,
        )
    }
}