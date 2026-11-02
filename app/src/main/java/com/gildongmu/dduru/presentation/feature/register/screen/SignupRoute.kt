package com.gildongmu.dduru.presentation.feature.register.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.presentation.feature.mypage.component.ConfirmDialog
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.viewmodel.SignupViewModel

@Composable
fun SignupRoute(
    onSignupComplete: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    LaunchedEffect(state.step) {
        if (state.step == SignupStep.DONE) {
            onSignupComplete()
        }
    }

    SignupScreen(
        state = state,
        years = viewModel.years,
        months = viewModel.months,
        days = viewModel.days,
        onYearSelected = viewModel::onYearSelected,
        onMonthSelected = viewModel::onMonthSelected,
        onDaySelected = viewModel::onDaySelected,
        onGenderSelect = viewModel::onGenderSelect,
        onPhoneChange = viewModel::onPhoneChange,
        onAuthCodeChange = viewModel::onAuthCodeChange,
        onPhoneComplete = viewModel::onPhoneComplete,
        onAuthComplete = viewModel::onAuthComplete,
        onRegisterComplete = viewModel::onRegisterComplete
    )

    if (state.isAuthAttemptExceeded) {
        ConfirmDialog(
            title = "인증 횟수를 초과했어요",
            description = "인증번호를 여러 번 잘못 입력했어요.\n인증번호를 다시 받아주세요.",
            onDismiss = viewModel::onAuthAttemptExceededDismiss,
            onConfirm = viewModel::onAuthAttemptExceededResend,
            dismissText = "닫기",
            confirmText = "다시 받기"
        )
    }
}
