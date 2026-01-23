package com.gildongmu.dduru_android.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text
import com.gildongmu.dduru_android.core.designsystem.theme.DduruTheme


@Composable
fun MaterialUnderlineTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    actionContent: (@Composable (() -> Unit))? = null
) {
    Column {
        val labelColor = if (enabled) Color.Black else Color.Black.copy(alpha = 0.4f)
        val placeholderColor = Color.Black.copy(alpha = if (enabled) 0.4f else 0.2f)

        Text(
            text = label,
            style = DduruTheme.typography.BodyMedium,
            color = labelColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = DduruTheme.typography.BodyLarge,
                        color = placeholderColor
                    )
                },
                textStyle = DduruTheme.typography.BodyLarge,
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = DduruTheme.colors.Primary,
                    unfocusedIndicatorColor =DduruTheme.colors.Primary.copy(alpha = 0.4f),
                    disabledIndicatorColor = DduruTheme.colors.Primary.copy(alpha = 0.2f),

                    cursorColor = DduruTheme.colors.Primary,
                    focusedTextColor = labelColor,
                    unfocusedTextColor = labelColor
                )
            )

            actionContent?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}


@Composable
fun UnderlineActionTextField(
    label: String,
    value: String,
    placeholder: String,
    actionText: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onActionClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            style = DduruTheme.typography.BodyMedium,
            color = DduruTheme.colors.Primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier.weight(1f),
                textStyle = DduruTheme.typography.BodyMedium,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp) ){
                            if (value.isEmpty()) {
                                Text(text = placeholder, color = DduruTheme.colors.Primary)
                                innerTextField()
                            }
                        }
                })
        }
        Spacer(modifier = Modifier.height(9.dp))

        OutlinedButton(onClick = onActionClick,enabled = enabled) {
            Text(actionText)
        }
    }
    DividerDefaults
}

@Composable
fun InlineActionButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    colors: ButtonColors? = null,
    border: BorderStroke? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(12.dp),
        border = border,
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = if (enabled) DduruTheme.colors.Primary else Color(0xFFE4E9F0),
            contentColor = if (enabled) Color.White else DduruTheme.colors.Primary.copy(alpha = 0.5f),
            disabledContainerColor = Color(0xFFE4E9F0),
            disabledContentColor = DduruTheme.colors.Primary.copy(alpha = 0.4f)
        )
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun RandomNicknameButtonPreview() {
    DduruTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RandomNicknameButton(enabled = true, onClick = {})
            InlineActionButton(text = "인증요청", enabled = true, onClick = {})
            InlineActionButton(
                text = "재전송",
                enabled = true,
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black.copy(alpha = 0.4f)
                ),
                border = BorderStroke(1.dp, Color(0xFFD5DAE2))
            )
        }
    }
}
