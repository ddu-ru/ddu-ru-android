package com.gildongmu.dduru.presentation.feature.matching.effect

sealed interface MatchingSideEffect {
    data object NavigateToNotification : MatchingSideEffect
    data object NavigateToMenu : MatchingSideEffect
    data object NavigateToCreatePost : MatchingSideEffect
    data class NavigateToPostDetail(val id: Long) : MatchingSideEffect
}
