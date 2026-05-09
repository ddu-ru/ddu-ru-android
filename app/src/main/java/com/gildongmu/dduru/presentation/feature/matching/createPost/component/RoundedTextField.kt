package com.gildongmu.dduru.presentation.feature.matching.createPost.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@Composable
fun RoundedMatchingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = 1
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp)
            ),
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        shape = RoundedCornerShape(99.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            focusedTextColor = DduruTheme.colors.gray900,
            unfocusedTextColor = DduruTheme.colors.gray600,
            focusedContainerColor = DduruTheme.colors.gray0,
            unfocusedContainerColor = DduruTheme.colors.gray0,
            focusedIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f),
            unfocusedIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f),
            disabledIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f)
        )
    )
}

@Preview(showBackground = true, name = "Rounded Search")
@Composable
private fun RoundedSearchTextFieldPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 200.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            RoundedMatchingTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "여행지 선택",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Body1
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_matching_searching),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray500
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoundedHashtagTextFieldPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 200.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            RoundedMatchingTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "해시태그 입력..",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Body1
                    )
                },
                leadingIcon = {
                    Text(
                        text = "#",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Body1
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoundedHashtagTextFieldLongDescriptionPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 200.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            RoundedMatchingTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "한글 4자 또는 영문/숫자 7자 이하로 입력해 주세요.",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Caption2
                    )
                },
                leadingIcon = {
                    Text(
                        text = "#",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Body1
                    )
                }
            )
        }
    }
}
