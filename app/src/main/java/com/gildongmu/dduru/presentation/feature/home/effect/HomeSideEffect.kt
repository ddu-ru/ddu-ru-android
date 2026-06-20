package com.gildongmu.dduru.presentation.feature.home.effect

sealed interface HomeSideEffect {
    data object NavigateToNotification : HomeSideEffect
    data object NavigateToMenu : HomeSideEffect
    data object NavigateToUpcomingTripTask : HomeSideEffect
    data class NavigateToDestination(val id: Long) : HomeSideEffect
    data class NavigateToSuperHost(val id: Long) : HomeSideEffect
    data class NavigateToCompanion(val id: Long) : HomeSideEffect
    data object NavigateToCreateTravelRoom : HomeSideEffect
    data object NavigateToWriteCommunity : HomeSideEffect
}
