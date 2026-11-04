package com.gildongmu.dduru.presentation.feature.chat.effect

import com.gildongmu.dduru.presentation.component.DduruToastMessage

sealed interface ChatRoomSideEffect {
    data object NavigateBack : ChatRoomSideEffect
    data object NavigateToMembers : ChatRoomSideEffect
    data object NavigateToMenu : ChatRoomSideEffect
    data object NavigateToPollCreate : ChatRoomSideEffect
    data object OpenPhotoPicker : ChatRoomSideEffect
    data class NavigateToImageViewer(val imageUrls: List<String>, val initialIndex: Int) : ChatRoomSideEffect
    data class ShowMessage(val message: String) : ChatRoomSideEffect
    data class ShowError(val toast: DduruToastMessage) : ChatRoomSideEffect
}
