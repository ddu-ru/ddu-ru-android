package com.gildongmu.dduru_android.presentation.feature.register.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gildongmu.dduru_android.core.designsystem.component.DduruButton
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru_android.presentation.feature.register.viewmodel.SignupViewModel
import com.gildongmu.dduru_android.presentation.feature.register.component.AuthCodeInput
import com.gildongmu.dduru_android.presentation.feature.register.component.BirthInputRow
import com.gildongmu.dduru_android.presentation.feature.register.component.GenderSegment
import com.gildongmu.dduru_android.presentation.feature.register.component.GreetingMessageCard
import com.gildongmu.dduru_android.presentation.feature.register.component.NicknameInput
import com.gildongmu.dduru_android.presentation.feature.register.component.PhoneInput
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupStep
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun SignupScreen(
    viewModel: SignupViewModel = viewModel()
) {
    val state = viewModel.uiState
    val currentStep = state.step
    val listState = rememberLazyListState()

    val visibleSteps = remember(currentStep) {
        SignupStep.entries.filter { it.ordinal <= currentStep.ordinal }
    }

    val isBottomButtonVisible = currentStep.ordinal >= SignupStep.NICKNAME.ordinal && currentStep != SignupStep.DONE

    LaunchedEffect(currentStep) {
        if (visibleSteps.isNotEmpty()) {
            delay(200)
            listState.animateScrollToItem(visibleSteps.size - 1)
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
            .background(DduruTheme.colors.Gray50)
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

                        SignupStep.NICKNAME -> {
                            NicknameInput(
                                modifier = Modifier,
                                value = state.nickname,
                                onValueChange = viewModel::onNicknameChange,
                                enabled = true,
                                onRandomClick = viewModel::onRandomNickname,
                                shouldAutoFocus = (currentStep == SignupStep.NICKNAME),
                                validationCheck = false,
                                validationMessage = "",
                            )
                        }

                        SignupStep.PHONE -> {
                            PhoneInput(
                                modifier = Modifier,
                                value = state.phoneNumber,
                                onValueChange = viewModel::onPhoneChange,
                                enabled = true,
                                shouldAutoFocus = (currentStep == SignupStep.PHONE),
                                validationMessage = state.phoneErrorMessage,
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
                                onResendClick = {
                                    viewModel.onPhoneComplete()
                                },
                                validationCheck = false,
                                validationMessage = "",
                            )
                        }

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
                SignupStep.NICKNAME -> Triple(
                    "다음",
                    state.nickname.isNotBlank(),
                    viewModel::onNicknameComplete
                )
                SignupStep.PHONE -> Triple(
                    "인증번호 전송",
                    state.phoneNumber.length >= 10,
                    viewModel::onPhoneComplete
                )
                SignupStep.AUTH -> Triple(
                    if (state.authCode.isNotEmpty()) "확인" else "인증요청",
                    state.authCode.isNotEmpty(),
                    viewModel::onAuthComplete
                )
                else -> Triple("", false, {})
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                DduruButton(
                    text = buttonText,
                    enabled = buttonEnabled,
                    onClick = onButtonClick,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
