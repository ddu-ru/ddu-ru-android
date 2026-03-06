package com.gildongmu.dduru.presentation.feature.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MaterialUnderlineNumberTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    maxLength: Int? = null,
    actionContent: (@Composable (() -> Unit))? = null
) {
    Column {
        val labelColor = if (enabled) Color.Black else Color.Black.copy(alpha = 0.4f)
        val placeholderColor = Color.Black.copy(alpha = if (enabled) 0.4f else 0.2f)

        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = labelColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = { input ->
                    val digitsOnly = input.filter { it.isDigit() }
                    val limited = maxLength?.let { digitsOnly.take(it) } ?: digitsOnly
                    onValueChange(limited)
                },
                enabled = enabled,
                modifier = modifier.weight(1f),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = DduruTheme.typography.Body1,
                        color = placeholderColor
                    )
                },
                textStyle = DduruTheme.typography.Body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = DduruTheme.colors.primary,
                    unfocusedIndicatorColor = DduruTheme.colors.primary.copy(alpha = 0.4f),
                    disabledIndicatorColor = DduruTheme.colors.primary.copy(alpha = 0.2f),

                    cursorColor = DduruTheme.colors.primary,
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
fun InlineActionButton(
    modifier: Modifier = Modifier,
    width: Dp = 110.dp,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    colors: ButtonColors? = null,
    border: BorderStroke? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(36.dp)
            .width(width),
        shape = RoundedCornerShape(12.dp),
        border = border,
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = DduruTheme.colors.primary,
            contentColor = Color.White,
            disabledContainerColor = DduruTheme.colors.gray200,
            disabledContentColor = DduruTheme.colors.gray400
        )
    ) {
        Text(
            text = text
        )
    }
}
