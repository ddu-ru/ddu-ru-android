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

    val birthEnabled = currentStep.ordinal >= SignupStep.BIRTH.ordinal
    val nicknameFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val authFocusRequester = remember { FocusRequester() }

    val sections = buildList<@Composable () -> Unit> {
        add {
            Column(modifier = Modifier.fillMaxWidth()) {
                BirthInputRow(
                    state = state,
                    viewModel = viewModel,
                    enabled = birthEnabled
                )
            }
        }

        if (currentStep.ordinal >= SignupStep.GENDER.ordinal) {
            val genderEnabled = currentStep.ordinal >= SignupStep.GENDER.ordinal
            add {
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        GenderSegment(
                            selected = state.gender,
                            onSelect = viewModel::onGenderSelect,
                            enabled = genderEnabled
                        )
                    }
                }
            }
        }

        if (currentStep.ordinal >= SignupStep.NICKNAME.ordinal) {
            val nicknameEnabled = currentStep.ordinal >= SignupStep.NICKNAME.ordinal
            add {
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        NicknameInput(
                            modifier = Modifier.focusRequester(nicknameFocusRequester),
                            value = state.nickname,
                            onValueChange = viewModel::onNicknameChange,
                            enabled = nicknameEnabled,
                            onRandomClick = viewModel::onRandomNickname
                        )
                    }
                }
            }
        }

        val showPhoneSection = currentStep.ordinal >= SignupStep.PHONE.ordinal || state.nickname.isNotBlank()
        if (showPhoneSection) {
            val phoneEnabled = showPhoneSection
            val phoneButtonEnabled = phoneEnabled
            add {
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        PhoneInput(
                            modifier = Modifier.focusRequester(phoneFocusRequester),
                            value = state.phoneNumber,
                            onValueChange = viewModel::onPhoneChange,
                            enabled = phoneEnabled,
                            actionEnabled = phoneButtonEnabled && state.phoneNumber.length >= 10,
                            onActionClick = {
                                viewModel.onPhoneComplete()
                                authFocusRequester.requestFocus()
                            },
                            isResendButton = state.isPhoneRequested
                        )
                    }
                }
            }
        }

        if (currentStep.ordinal >= SignupStep.AUTH.ordinal) {
            val authEnabled = currentStep.ordinal >= SignupStep.AUTH.ordinal
            val authButtonEnabled = currentStep == SignupStep.AUTH
            add {
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AuthCodeInput(
                            modifier = Modifier.focusRequester(authFocusRequester),
                            value = state.authCode,
                            onValueChange = viewModel::onAuthCodeChange,
                            enabled = authEnabled,
                            actionEnabled = authButtonEnabled && state.authCode.length >= 4,
                            onActionClick = viewModel::onAuthComplete,
                            timerText = formatTimer(state.authTimerSeconds)
                        )
                    }
                }
            }
        }

        if (currentStep == SignupStep.DONE) {
            add {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("회원가입 완료 🎉", color = Color.Blue)
                }
            }
        }
    }

    LaunchedEffect(currentStep) {
        delay(100)
        if (sections.isNotEmpty()) {
            val lastIndex = sections.size - 1
            listState.animateScrollToItem(lastIndex)
        }
        when (currentStep) {
            SignupStep.NICKNAME -> nicknameFocusRequester.requestFocus()
            SignupStep.PHONE -> phoneFocusRequester.requestFocus()
            SignupStep.AUTH -> authFocusRequester.requestFocus()
            else -> {}
        }
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
                items = sections,
                key = { sections.indexOf(it) }
            ) { content ->
                // animateItem()은 리스트 안에서 아이템이 추가/이동될 때 위치를 부드럽게 잡아줌
                Column(modifier = Modifier.animateItem()) {
                    content()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (currentStep.ordinal >= SignupStep.AUTH.ordinal) {
            val (buttonText, buttonEnabled, onButtonClick) = when (currentStep) {
                SignupStep.AUTH -> Triple(
                    if (state.authCode.isNotEmpty()) "확인" else "인증완료",
                    state.authCode.isNotEmpty(),
                    viewModel::onAuthComplete
                )
                SignupStep.DONE -> Triple("완료", true, {})
                else -> Triple("", false, {})
            }

            DduruButton(
                text = buttonText,
                enabled = buttonEnabled,
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun formatTimer(seconds: Int): String {
    val safe = seconds.coerceAtLeast(0)
    val minutes = safe / 60
    val secs = safe % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
}

@Preview
@Composable
private fun SignupScreenPReview() {
    SignupScreen()
}

//NicknameInpu2t(
//value = state.nickname,
//onValueChange = viewModel::onNicknameChange,
//onCheckClick = viewModel::onCheckNickname,
//onRandomClick = viewModel::onGenerateNickname,
//isAvailable = state.isNicknameAvailable
//)
