package com.gildongmu.dduru.presentation.feature.chat.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gildongmu.dduru.presentation.feature.chat.viewmodel.ChatRoomMenuViewModel

@Composable
fun ChatRoomMenuRoute(
    chatRoomId: Long,
    onBackClick: () -> Unit,
    viewModel: ChatRoomMenuViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(chatRoomId) {
        viewModel.load(chatRoomId)
    }

    LaunchedEffect(Unit) {
        viewModel.message.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    ChatRoomMenuScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onJourneyClick = viewModel::showNotReady,
        onNotificationsChange = { viewModel.showNotReady() },
        onContentTabSelected = viewModel::onContentTabSelected,
        onViewAllContentClick = viewModel::showNotReady,
        onReportClick = viewModel::showNotReady,
        onLeaveClick = viewModel::onLeaveClick,
        onLeaveDismiss = viewModel::onLeaveDismiss,
        onLeaveConfirm = viewModel::onLeaveConfirm
    )
}
