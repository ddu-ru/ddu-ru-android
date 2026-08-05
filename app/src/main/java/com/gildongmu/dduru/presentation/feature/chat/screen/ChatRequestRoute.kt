package com.gildongmu.dduru.presentation.feature.chat.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.core.common.state.UiState
import com.gildongmu.dduru.core.designsystem.component.DduruFailureScreen
import com.gildongmu.dduru.core.designsystem.component.DduruLoadingScreen
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatRequestViewModel

@Composable
fun ChatRequestRoute(
    viewModel: ChatRequestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is UiState.Loading -> DduruLoadingScreen()
        is UiState.Success -> ChatRequestScreen(
            uiState = state.data,
            onAcceptConfirm = viewModel::onAcceptConfirm,
            onRejectConfirm = viewModel::onRejectConfirm
        )
        is UiState.Failure -> DduruFailureScreen(message = "요청 목록을 불러올 수 없습니다.")
        else -> Unit
    }
}
