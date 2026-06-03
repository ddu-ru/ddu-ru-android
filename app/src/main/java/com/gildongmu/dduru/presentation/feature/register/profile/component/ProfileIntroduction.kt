package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.MultiLineTextField

const val PROFILE_INTRODUCTION_MAX_LENGTH = 60

@Composable
fun ProfileIntroduction(
    text: String,
    modifier: Modifier = Modifier,
    maxLength: Int = PROFILE_INTRODUCTION_MAX_LENGTH,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "자기소개",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(Modifier.height(8.dp))

        MultiLineTextField(
            value = text,
            onValueChange = { newValue ->
                onValueChange(newValue.take(maxLength))
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            maxLength = maxLength,
            placeholder = "나를 소개하는 한마디를 작성해주세요!"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileIntroductionPreview() {
    DduruTheme {
        var text by remember { mutableStateOf("") }

        ProfileIntroduction(
            text = text,
            onValueChange = { text = it }
        )
    }
}
