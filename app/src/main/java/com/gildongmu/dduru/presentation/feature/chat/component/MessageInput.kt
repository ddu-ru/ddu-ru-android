package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
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
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onPlusClick: () -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasText = value.isNotBlank()
    val fieldShape = RoundedCornerShape(24.dp)
    val plusShape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 40.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .dduruDualShadow(
                    firstAlpha = 0.10f,
                    secondAlpha = 0.08f,
                    shape = plusShape
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                    shape = plusShape
                )
                .background(
                    color = DduruTheme.colors.gray0.copy(alpha = 0.50f),
                    shape = plusShape
                )
                .noRippleClickable(onClick = onPlusClick)
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus_black),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = DduruTheme.colors.black
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .weight(1f)
                .dduruDualShadow(
                    firstAlpha = 0.10f,
                    secondAlpha = 0.08f,
                    shape = fieldShape
                )
                .border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                    shape = fieldShape
                )
                .background(
                    color = DduruTheme.colors.gray0.copy(alpha = 0.50f),
                    shape = fieldShape
                )
                .padding(4.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = DduruTheme.typography.Body2.copy(
                    color = DduruTheme.colors.black
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
                    .padding(vertical = 10.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "메세지를 입력하세요..",
                            style = DduruTheme.typography.Body2,
                            color = DduruTheme.colors.gray500
                        )
                    }
                    innerTextField()
                }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = 48.dp, height = 40.dp)
                    .border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                        shape = fieldShape
                    )
                    .background(
                        color = if (hasText) DduruTheme.colors.primary else DduruTheme.colors.gray100,
                        shape = fieldShape
                    )
                    .noRippleClickable(onClick = { if (hasText) onSendClick() })
                    .padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (hasText) DduruTheme.colors.gray0 else DduruTheme.colors.gray300
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageInputEmptyPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("") }
        MessageInput(
            value = text,
            onValueChange = { text = it },
            onPlusClick = {},
            onSendClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageInputWithTextPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("안녕하세요!") }
        MessageInput(
            value = text,
            onValueChange = { text = it },
            onPlusClick = {},
            onSendClick = {}
        )
    }
}
