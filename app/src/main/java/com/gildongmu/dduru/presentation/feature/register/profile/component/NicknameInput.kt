package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.SingleLineTextField
import com.gildongmu.dduru.presentation.feature.register.component.ValidationMessage

@Composable
fun NicknameInput(
    text: String,
    validationMessage: String,
    modifier: Modifier = Modifier,
    useProfileEditStyle: Boolean = false,
    onValueChange: (String) -> Unit,
    onRandomClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "닉네임",
            style = if (useProfileEditStyle) DduruTheme.typography.Body3 else DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SingleLineTextField(
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                maxLength = 10,
                placeholder = if (useProfileEditStyle) "닉네임 입력" else "닉네임을 입력하세요",
                showCounter = !useProfileEditStyle
            )

            Spacer(Modifier.width(12.dp))

            RandomNicknameButton(
                useProfileEditStyle = useProfileEditStyle,
                modifier = if (useProfileEditStyle) Modifier.size(width = 120.dp, height = 48.dp) else Modifier,
                onClick = onRandomClick
            )
        }
        if (validationMessage.isNotBlank()) {
            ValidationMessage(
                text = validationMessage
            )
        }
    }
}

@Composable
private fun RandomNicknameButton(
    useProfileEditStyle: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = DduruTheme.colors.gray0,
        border = BorderStroke(
            width = 1.dp,
            color = DduruTheme.colors.gray958.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = if (useProfileEditStyle) {
                Modifier
            } else {
                Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_random),
                contentDescription = null,
                modifier = if (useProfileEditStyle) Modifier.size(16.dp) else Modifier,
                tint = DduruTheme.colors.gray700
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "랜덤",
                style = if (useProfileEditStyle) DduruTheme.typography.Title1 else DduruTheme.typography.Title,
                color = DduruTheme.colors.gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameInputPreview() {
    var nickname by remember { mutableStateOf("") }

    DduruTheme {
        NicknameInput(
            modifier = Modifier.padding(16.dp),
            text = nickname,
            onValueChange = { nickname = it },
            onRandomClick = { nickname = "뚜르" },
            validationMessage = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NicknameInputValidationMessagePreview() {
    var nickname by remember { mutableStateOf("") }

    DduruTheme {
        NicknameInput(
            modifier = Modifier.padding(16.dp),
            text = nickname,
            onValueChange = { nickname = it },
            onRandomClick = { nickname = "뚜르" },
            validationMessage = "닉네임을 입력해주세요"
        )
    }
}
