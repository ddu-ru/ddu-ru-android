package com.gildongmu.dduru.presentation.feature.register.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.component.AuthCodeInput
import com.gildongmu.dduru.presentation.feature.register.component.BirthInputRow
import com.gildongmu.dduru.presentation.feature.register.component.GenderSegment
import com.gildongmu.dduru.presentation.feature.register.component.GreetingMessageCard
import com.gildongmu.dduru.presentation.feature.register.component.PhoneInput
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.viewmodel.SignupViewModel
import java.util.Locale
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = viewModel()
) {
    val state = viewModel.uiState
    val currentStep = state.step
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val SIGNUP_FLOW = remember {
        listOf(
            SignupStep.BIRTH,
            SignupStep.GENDER,
            SignupStep.PHONE,
            SignupStep.AUTH,
            SignupStep.AUTH_VERIFICATION,
            SignupStep.DONE
        )
    }

    val visibleSteps = remember(currentStep) {
        val idx = SIGNUP_FLOW.indexOf(currentStep).takeIf { it >= 0 } ?: 0
        SIGNUP_FLOW.take(idx + 1)
    }

    val isBottomButtonVisible = when (currentStep) {
        SignupStep.PHONE,
        SignupStep.AUTH,
        SignupStep.AUTH_VERIFICATION -> true
        else -> false
    }

    LaunchedEffect(currentStep) {
        if (visibleSteps.isNotEmpty()) {
            delay(200)
            listState.animateScrollToItem(visibleSteps.size - 1)
        }
        if (currentStep == SignupStep.AUTH_VERIFICATION) {
            keyboardController?.hide()
        }
    }

    fun formatTimer(seconds: Int): String {
        val safeSeconds = seconds.coerceAtLeast(0)
        val minutes = safeSeconds / 60
        val secs = safeSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DduruTheme.colors.gray50)
            .statusBarsPadding()
            .imePadding()
    ) {
        GreetingMessageCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            step = state.step
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            reverseLayout = true
        ) {
            items(
                items = visibleSteps,
                key = { it.name }
            ) { step ->
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
                    modifier = Modifier.animateItem()
                ) {
                    when (step) {
                        SignupStep.BIRTH -> {
                            BirthInputRow(
                                state = state,
                                viewModel = viewModel,
                                enabled = true
                            )
                        }

                        SignupStep.GENDER -> {
                            GenderSegment(
                                selected = state.gender,
                                onSelect = viewModel::onGenderSelect,
                                enabled = true
                            )
                        }

                        SignupStep.PHONE -> {
                            PhoneInput(
                                modifier = Modifier,
                                value = state.phoneNumber,
                                onValueChange = viewModel::onPhoneChange,
                                enabled = true,
                                shouldAutoFocus = (currentStep == SignupStep.PHONE),
                                validationMessage = state.phoneErrorMessage
                            )
                        }

                        SignupStep.AUTH -> {
                            AuthCodeInput(
                                modifier = Modifier,
                                value = state.authCode,
                                onValueChange = viewModel::onAuthCodeChange,
                                enabled = true,
                                timerText = formatTimer(state.authTimerSeconds),
                                shouldAutoFocus = (currentStep == SignupStep.AUTH),
                                onResendClick = viewModel::onPhoneComplete,
                                validationMessage = state.authCodeErrorMessage
                            )
                        }

                        SignupStep.AUTH_VERIFICATION -> {}

                        SignupStep.DONE -> {
                            Text(
                                "회원가입 완료 🎉",
                                color = Color.Blue,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        if (isBottomButtonVisible) {
            val (buttonText, buttonEnabled, onButtonClick) = when (currentStep) {
                SignupStep.PHONE -> Triple(
                    "인증번호 전송",
                    state.phoneNumber.length >= 10,
                    viewModel::onPhoneComplete
                )
                SignupStep.AUTH -> Triple(
                    "확인",
                    state.authCode.isNotEmpty(),
                    viewModel::onAuthComplete
                )

                SignupStep.AUTH_VERIFICATION -> Triple(
                    "다음",
                    true,
                    viewModel::onRegisterComplete
                )

                else -> Triple("", false, {})
            }

            val isImeVisible = WindowInsets.isImeVisible
            val horizontalPadding = if (isImeVisible) 0.dp else 24.dp
            val bottomPadding = if (isImeVisible) {
                0.dp
            } else {
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 12.dp
            }
            val buttonShape = if (isImeVisible) {
                RoundedCornerShape(0.dp)
            } else {
                RoundedCornerShape(16.dp)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .padding(bottom = bottomPadding)
            ) {
                DduruButton(
                    text = buttonText,
                    enabled = buttonEnabled,
                    onClick = onButtonClick,
                    shape = buttonShape,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isImeVisible) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    DduruTheme {
        SignupScreen()
    }
}
