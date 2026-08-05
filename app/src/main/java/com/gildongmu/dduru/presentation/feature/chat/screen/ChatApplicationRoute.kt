package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.chat.effect.ChatApplicationSideEffect
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatApplicationViewModel

@Composable
fun ChatApplicationRoute(
    onNavigateToChatRoom: (Long) -> Unit,
    viewModel: ChatApplicationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ChatApplicationSideEffect.NavigateToChatRoom -> onNavigateToChatRoom(effect.chatRoomId)
            }
        }
    }

    when (val state = uiState) {
        is UiState.Loading -> DduruLoadingScreen()
        is UiState.Success -> ChatApplicationScreen(
            uiState = state.data,
            onCancelClick = viewModel::onCancelClick,
            onEnterClick = viewModel::onEnterClick
        )
        is UiState.Failure -> DduruFailureScreen(message = "신청 내역을 불러올 수 없습니다.")
        else -> Unit
    }
}
