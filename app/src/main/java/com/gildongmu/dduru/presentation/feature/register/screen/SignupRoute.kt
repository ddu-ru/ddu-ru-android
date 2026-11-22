package com.gildongmu.dduru.presentation.feature.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.DduruToastMessage
import com.gildongmu.dduru.presentation.component.LocalDduruErrorToast
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.viewmodel.SignupViewModel

@Composable
fun SignupRoute(
    onSignupComplete: () -> Unit = {},
    onLoginExpired: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val errorToast = LocalDduruErrorToast.current

    LaunchedEffect(state.step) {
        if (state.step == SignupStep.DONE) {
            onSignupComplete()
        }
    }

    LaunchedEffect(state.toast) {
        state.toast?.let { toast ->
            errorToast.show(
                DduruToastMessage(
                    title = context.getString(toast.titleRes),
                    message = context.getString(toast.messageRes)
                )
            )
            viewModel.onToastDismiss()
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

    if (state.isLoginExpired) {
        LoginExpiredDialog(onConfirm = onLoginExpired)
    }
}

@Composable
private fun LoginExpiredDialog(onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onConfirm) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(DduruTheme.colors.gray0, RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_expired_title),
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.gray900
                )
                Text(
                    text = stringResource(R.string.login_expired_description),
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray600
                )
            }

            DduruButton(
                text = stringResource(R.string.login_expired_confirm),
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}
