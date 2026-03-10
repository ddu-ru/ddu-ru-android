package com.gildongmu.dduru.presentation.feature.register.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.component.AuthCodeInput
import com.gildongmu.dduru.presentation.feature.register.component.BirthInputRow
import com.gildongmu.dduru.presentation.feature.register.component.GenderSegment
import com.gildongmu.dduru.presentation.feature.register.component.GreetingMessageCard
import com.gildongmu.dduru.presentation.feature.register.component.PhoneInput
import com.gildongmu.dduru.presentation.feature.register.state.Gender
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.state.SignupUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupScreen(
    state: SignupUiState,
    years: List<Int>,
    months: List<Int>,
    days: List<Int>,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    onDaySelected: (Int) -> Unit,
    onGenderSelect: (Gender) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAuthCodeChange: (String) -> Unit,
    onPhoneComplete: () -> Unit,
    onAuthComplete: () -> Unit,
    onRegisterComplete: () -> Unit
) {
    val currentStep = state.step
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(currentStep) {
        if (state.visibleSteps.isNotEmpty()) {
            delay(200)
            listState.animateScrollToItem(state.visibleSteps.size - 1)
        }
        if (currentStep == SignupStep.AUTH_VERIFICATION) {
            keyboardController?.hide()
        }
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
                items = state.visibleSteps,
                key = { it.name }
            ) { step ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically(),
                    modifier = Modifier.animateItem()
                ) {
                    when (step) {
                        SignupStep.BIRTH -> {
                            BirthInputRow(
                                year = state.year,
                                month = state.month,
                                day = state.day,
                                years = years,
                                months = months,
                                days = days,
                                onYearSelected = onYearSelected,
                                onMonthSelected = onMonthSelected,
                                onDaySelected = onDaySelected
                            )
                        }

                        SignupStep.GENDER -> {
                            GenderSegment(
                                selected = state.gender,
                                onSelect = onGenderSelect,
                                enabled = true
                            )
                        }

                        SignupStep.PHONE -> {
                            PhoneInput(
                                modifier = Modifier,
                                value = state.phoneNumber,
                                onValueChange = onPhoneChange,
                                enabled = true,
                                shouldAutoFocus = currentStep == SignupStep.PHONE,
                                validationMessage = state.phoneErrorMessage
                            )
                        }

                        SignupStep.AUTH -> {
                            AuthCodeInput(
                                modifier = Modifier,
                                value = state.authCode,
                                onValueChange = onAuthCodeChange,
                                enabled = true,
                                timerText = state.timerText,
                                shouldAutoFocus = currentStep == SignupStep.AUTH,
                                onResendClick = onPhoneComplete,
                                validationMessage = state.authCodeErrorMessage
                            )
                        }

                        SignupStep.AUTH_VERIFICATION -> Unit

                        SignupStep.DONE -> {
                            Text(
                                text = "회원가입 완료 🎉",
                                color = Color.Blue,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        if (state.bottomButton.visible) {
            val onBottomButtonClick = when (state.step) {
                SignupStep.PHONE -> onPhoneComplete
                SignupStep.AUTH -> onAuthComplete
                SignupStep.AUTH_VERIFICATION -> onRegisterComplete
                else -> ({})
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
                    text = state.bottomButton.text,
                    enabled = state.bottomButton.enabled,
                    onClick = onBottomButtonClick,
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
        SignupScreen(
            state = SignupUiState(),
            years = (1990..2025).toList(),
            months = (1..12).toList(),
            days = (1..31).toList(),
            onYearSelected = { _: Int -> },
            onMonthSelected = { _: Int -> },
            onDaySelected = { _: Int -> },
            onGenderSelect = { _: Gender -> },
            onPhoneChange = { _: String -> },
            onAuthCodeChange = { _: String -> },
            onPhoneComplete = {},
            onAuthComplete = {},
            onRegisterComplete = {}
        )
    }
}
