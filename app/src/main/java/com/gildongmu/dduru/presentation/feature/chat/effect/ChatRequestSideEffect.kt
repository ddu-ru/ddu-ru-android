package com.gildongmu.dduru.presentation.feature.chat.effect

import com.gildongmu.dduru.presentation.component.DduruToastMessage

sealed interface ChatRequestSideEffect {
    data class NavigateToChatRoom(val chatRoomId: Long) : ChatRequestSideEffect
    data class ShowError(val toast: DduruToastMessage) : ChatRequestSideEffect
}
