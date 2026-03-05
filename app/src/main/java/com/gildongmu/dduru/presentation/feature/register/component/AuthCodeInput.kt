package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlinx.coroutines.delay

@Composable
fun AuthCodeInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    timerText: String,
    onResendClick: () -> Unit,
    shouldAutoFocus: Boolean = false,
    validationMessage: String
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(shouldAutoFocus) {
        if (shouldAutoFocus && enabled) {
            delay(200)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Column() {
        MaterialUnderlineNumberTextField(
            modifier = modifier.focusRequester(focusRequester),
            label = "인증번호",
            value = value,
            placeholder = timerText,
            onValueChange = onValueChange,
            enabled = enabled,
            actionContent = {
                InlineActionButton(
                    text = "재전송",
                    enabled = enabled,
                    onClick = onResendClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = DduruTheme.colors.gray700,
                        disabledContainerColor = Color.White,
                        disabledContentColor = DduruTheme.colors.gray700
                    ),
                    border = BorderStroke(1.dp, DduruTheme.colors.gray300)
                )
            }
        )
        if (validationMessage.isNotBlank()) {
            ValidationMessage(
                text = validationMessage
            )
        }
    }
}
