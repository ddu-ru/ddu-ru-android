package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.gildongmu.dduru.presentation.feature.matching.createPost.component.RoundedMatchingTextField

@Composable
fun ClearableSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderMessage: String,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    RoundedMatchingTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholderMessage,
                color = DduruTheme.colors.gray500,
                style = DduruTheme.typography.Body1
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_x_gray),
                        contentDescription = "지우기",
                        tint = DduruTheme.colors.gray400
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_matching_searching),
                    contentDescription = "검색",
                    tint = DduruTheme.colors.gray500
                )
            }
        },
        maxLines = 1
    )
}

@Preview(showBackground = true)
@Composable
private fun ClearableSearchTextFieldPreview() {
    DduruTheme {
        var search by remember { mutableStateOf("일본") }

        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ClearableSearchTextField(
                value = search,
                onValueChange = { search = it },
                placeholderMessage = "여행지 선택",
                modifier = Modifier.size(width = 344.dp, height = 48.dp)
            )
        }
    }
}
