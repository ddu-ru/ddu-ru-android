package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.runtime.Composable

@Composable
fun AuthCodeInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    actionEnabled: Boolean,
    onActionClick: () -> Unit,
    timerText: String
) {
    MaterialUnderlineTextField(
        label = "인증번호",
        value = value,
        placeholder = timerText,
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            InlineActionButton(
                text = "인증완료",
                enabled = actionEnabled,
                onClick = onActionClick
            )
        }
    )
}
