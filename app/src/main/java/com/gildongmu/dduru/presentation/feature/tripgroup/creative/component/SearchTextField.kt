package com.gildongmu.dduru.presentation.feature.tripgroup.creative.component

import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    placeholderMessage: String,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(99.dp),
            ),
        placeholder = {
            Text(
                text = placeholderMessage,
                color = DduruTheme.colors.gray500,
                style = DduruTheme.typography.Body1
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearchClick()
            },
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onSearchClick()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_matching_searching),
                    contentDescription = "검색",
                    tint = DduruTheme.colors.gray500,
                )
            }
        },
        maxLines = 1,
        shape = RoundedCornerShape(99.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = DduruTheme.colors.gray900,
            unfocusedTextColor = DduruTheme.colors.gray600, // Todo() 나중에 바뀔 가능성 있음,
            focusedContainerColor = DduruTheme.colors.gray0,
            unfocusedContainerColor = DduruTheme.colors.gray0,
            focusedIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f),
            unfocusedIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f),
            disabledIndicatorColor = DduruTheme.colors.gray0.copy(alpha = 0f),
        )

    )
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    DduruTheme {
        var search by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 200.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            SearchTextField(
                value = search,
                onValueChange = { search = it },
                onSearchClick = {},
                placeholderMessage = "여행지 선택",
            )
        }
    }
}
