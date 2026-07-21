package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatMainSideEffect
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatMainViewModel

@Composable
fun ChatMainRoute(
    onChatRoomClick: (Long) -> Unit,
    viewModel: ChatMainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatMainSideEffect.NavigateToChatRoom -> onChatRoomClick(effect.chatRoomId)
            }
        }
    }

    when (val state = uiState) {
        is UiState.Success -> ChatMainScreen(
            chatRooms = state.data.chatRooms,
            onChatRoomClick = { id -> viewModel.onChatRoomClick(id.toLong()) },
            requestContent = {},
            applicationContent = {}
        )
        else -> Unit
    }
}
