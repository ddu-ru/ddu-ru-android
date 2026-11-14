package com.gildongmu.dduru.presentation.feature.matching.createPost.uistate

sealed interface MatchingCreatePostUiEvent {

    data object Close : MatchingCreatePostUiEvent

    data object Complete : MatchingCreatePostUiEvent

    data class ShowMessage(val message: String) : MatchingCreatePostUiEvent
}
