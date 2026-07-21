package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatRoomSideEffect
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatRoomViewModel

@Composable
fun ChatRoomRoute(
    chatRoomId: Long,
    onBackClick: () -> Unit,
    viewModel: ChatRoomViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(chatRoomId) {
        viewModel.loadMessages(chatRoomId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                ChatRoomSideEffect.NavigateBack -> onBackClick()
                ChatRoomSideEffect.NavigateToMembers -> Unit
                ChatRoomSideEffect.NavigateToMenu -> Unit
            }
        }
    }

    ChatRoomScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onMembersClick = viewModel::onMembersClick,
        onMenuClick = viewModel::onMenuClick,
        onPlusClick = viewModel::onPlusClick,
        onSendClick = viewModel::onSendClick,
        onInputChange = viewModel::onInputChange
    )
}
