package com.gildongmu.dduru.presentation.feature.register.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.presentation.feature.register.viewmodel.SignupViewModel

@Composable
fun SignupRoute(
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

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
}
