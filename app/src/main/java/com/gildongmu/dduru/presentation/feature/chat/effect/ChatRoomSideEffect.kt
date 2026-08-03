package com.gildongmu.dduru.presentation.feature.chat.effect

sealed interface ChatRoomSideEffect {
    data object NavigateBack : ChatRoomSideEffect
    data object NavigateToMembers : ChatRoomSideEffect
    data object NavigateToMenu : ChatRoomSideEffect
}
