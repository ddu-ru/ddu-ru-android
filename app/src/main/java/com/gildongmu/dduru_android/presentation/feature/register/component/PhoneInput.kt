package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.delay

@Composable
fun PhoneInput(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
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
            label = "전화번호",
            value = value,
            placeholder = "01012341234",
            onValueChange = onValueChange,
            enabled = enabled,
        )
        if(validationMessage.isNotBlank()) {
            ValidationMessage(
                text = validationMessage,
            )
        }
    }
}
