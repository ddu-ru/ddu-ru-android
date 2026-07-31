package com.gildongmu.dduru.presentation.feature.chat.effect

sealed interface ChatMainSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Long) : ChatMainSideEffect
}
