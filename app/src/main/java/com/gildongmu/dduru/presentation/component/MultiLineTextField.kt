package com.gildongmu.dduru.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MultiLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = DduruTheme.colors.gray200
        ),
        color = DduruTheme.colors.gray0
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 17.dp),
            textStyle = DduruTheme.typography.Body2.copy(
                color = DduruTheme.colors.gray958
            ),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = DduruTheme.typography.Body2,
                                color = DduruTheme.colors.gray400
                            )
                        }

                        innerTextField()
                    }

                    Text(
                        text = "${value.length}/$maxLength",
                        modifier = Modifier.fillMaxWidth(),
                        style = DduruTheme.typography.Caption2,
                        textAlign = TextAlign.End,
                        color = if (value.isEmpty()) {
                            DduruTheme.colors.gray500
                        } else {
                            DduruTheme.colors.gray700
                        }
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MultiLineTextFieldPreview() {
    DduruTheme {
        var value by remember { mutableStateOf("") }

        Surface(
            color = DduruTheme.colors.gray0,
            modifier = Modifier.padding(24.dp).height(300.dp)
        ) {
            MultiLineTextField(
                value = value,
                onValueChange = { value = it },
                maxLength = 200,
                placeholder = "내용을 입력해 주세요."
            )
        }
    }
}
