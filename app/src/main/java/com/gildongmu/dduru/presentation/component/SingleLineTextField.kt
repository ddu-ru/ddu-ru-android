package com.gildongmu.dduru.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun SingleLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        singleLine = true,
        textStyle = DduruTheme.typography.Body1.copy(
            color = DduruTheme.colors.gray958
        ),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 13.dp, bottom = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = DduruTheme.typography.Body1,
                                color = DduruTheme.colors.gray400
                            )
                        }

                        innerTextField()
                    }

                    Text(
                        text = "${value.length}/$maxLength",
                        style = DduruTheme.typography.Caption2,
                        color = if (value.isEmpty()) {
                            DduruTheme.colors.gray500
                        } else {
                            DduruTheme.colors.gray700
                        }
                    )
                }

                HorizontalDivider(
                    thickness = 2.dp,
                    color = DduruTheme.colors.gray100
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SingleLineTextFieldTitlePreview() {
    DduruTheme {
        var value by remember { mutableStateOf("") }

        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.padding(24.dp)
        ) {
            SingleLineTextField(
                value = value,
                onValueChange = { value = it },
                maxLength = 40,
                placeholder = "제목은 최소 5자 이상입니다."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SingleLineTextFieldNicknamePreview() {
    DduruTheme {
        var value by remember { mutableStateOf("") }

        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.padding(24.dp)
        ) {
            SingleLineTextField(
                value = value,
                onValueChange = { value = it },
                maxLength = 11,
                placeholder = "닉네임 입력"
            )
        }
    }
}
