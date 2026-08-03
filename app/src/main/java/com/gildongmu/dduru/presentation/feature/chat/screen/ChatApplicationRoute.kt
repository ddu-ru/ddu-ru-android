package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
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
        is UiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        is UiState.Success -> ChatApplicationScreen(
            uiState = state.data,
            onCancelClick = viewModel::onCancelClick,
            onEnterClick = viewModel::onEnterClick
        )
        is UiState.Failure -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "신청 내역을 불러올 수 없습니다.")
        }
        else -> Unit
    }
}
