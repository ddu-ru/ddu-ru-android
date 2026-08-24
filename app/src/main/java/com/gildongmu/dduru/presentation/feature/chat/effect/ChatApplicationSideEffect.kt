package com.gildongmu.dduru.presentation.feature.chat.effect

sealed interface ChatApplicationSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Long) : ChatApplicationSideEffect
}
