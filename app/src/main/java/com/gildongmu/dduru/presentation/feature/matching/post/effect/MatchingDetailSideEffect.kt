package com.gildongmu.dduru.presentation.feature.matching.post.effect

sealed interface MatchingDetailSideEffect {
    data class NavigateToChatRoom(val roomId: Long) : MatchingDetailSideEffect
    data class ShowMessage(val message: String) : MatchingDetailSideEffect
}
