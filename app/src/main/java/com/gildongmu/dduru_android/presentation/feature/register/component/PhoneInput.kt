package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PhoneInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    actionEnabled: Boolean,
    onActionClick: () -> Unit,
    isResendButton: Boolean
) {
    MaterialUnderlineTextField(
        label = "전화번호",
        value = value,
        placeholder = "01012341234",
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            InlineActionButton(
                text = if (isResendButton) "재전송" else "인증요청",
                enabled = actionEnabled,
                onClick = onActionClick,
                colors = if (isResendButton) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black.copy(alpha = 0.4f)
                    )
                } else {
                    null
                },
                border = if (isResendButton) BorderStroke(1.dp, Color(0xFFD5DAE2)) else null
            )
        }
    )
}
