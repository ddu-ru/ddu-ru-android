package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlinx.coroutines.delay

@Composable
fun NicknameInput(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    onRandomClick: () -> Unit,
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
        MaterialUnderlineTextField(
            modifier = modifier.focusRequester(focusRequester),
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
        if (validationMessage.isNotBlank()) {
            ValidationMessage(
                text = validationMessage
            )
        }
    }
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
        Icon(
            painter = painterResource(id = R.drawable.ic_random),
            contentDescription = "랜덤 닉네임 생성",
            modifier = Modifier.size(16.dp).padding(1.dp),
            tint = DduruTheme.colors.Gray700
        )

        Text(
            modifier = Modifier.padding(1.dp),
            text = "랜덤",
            style = DduruTheme.typography.Button,
            color = DduruTheme.colors.Gray700
        )
    }
}
