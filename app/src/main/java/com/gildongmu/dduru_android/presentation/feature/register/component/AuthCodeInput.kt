package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme
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
    validationMessage : String,
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
                    textColor = DduruTheme.colors.Gray600,
                    enabled = enabled,
                    onClick = onResendClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = DduruTheme.colors.Gray900,
                        disabledContainerColor = Color.White,
                        disabledContentColor = DduruTheme.colors.Gray400
                    ),
                    border = BorderStroke(1.dp, DduruTheme.colors.Gray300)
                )
            }
        )
        if(validationMessage.isNotBlank()) {
            ValidationMessage(
                text = validationMessage,
            )
        }
    }
}
