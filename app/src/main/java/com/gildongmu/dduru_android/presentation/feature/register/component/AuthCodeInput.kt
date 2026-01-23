package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthCodeInput(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    actionEnabled: Boolean,
    onActionClick: () -> Unit,
    timerText: String
) {
    MaterialUnderlineTextField(
        modifier = modifier,
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
