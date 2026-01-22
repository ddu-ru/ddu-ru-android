package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text

@Composable
fun NicknameInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    onRandomClick: () -> Unit
) {
    MaterialUnderlineTextField(
        label = "닉네임",
        value = value,
        placeholder = "겨울이요길",
        onValueChange = onValueChange,
        enabled = enabled,
        actionContent = {
            RandomNicknameButton(
                enabled = enabled,
                onClick = onRandomClick
            )
        }
    )
}

@Composable
fun RandomNicknameButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Black
        )
    ) {
        Text(text = "랜덤")
    }
}
