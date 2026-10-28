package com.gildongmu.dduru.presentation.feature.chat.effect

sealed interface ChatRequestSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Long) : ChatRequestSideEffect
    data class ShowMessage(val message: String) : ChatRequestSideEffect
}
