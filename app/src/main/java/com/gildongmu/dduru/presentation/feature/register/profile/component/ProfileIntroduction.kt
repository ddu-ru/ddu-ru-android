package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.MultiLineTextField

const val PROFILE_INTRODUCTION_MAX_LENGTH = 60

@Composable
fun ProfileIntroduction(
    text: String,
    modifier: Modifier = Modifier,
    maxLength: Int = PROFILE_INTRODUCTION_MAX_LENGTH,
    useProfileEditStyle: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "자기소개",
            style = if (useProfileEditStyle) {
                DduruTheme.typography.Body1.copy(lineHeight = 19.sp)
            } else {
                DduruTheme.typography.Body2
            },
            color = if (useProfileEditStyle) DduruTheme.colors.black else DduruTheme.colors.gray800
        )

        Spacer(Modifier.height(if (useProfileEditStyle) 16.dp else 8.dp))

        if (useProfileEditStyle) {
            MultiLineTextField(
                value = text,
                onValueChange = { newValue ->
                    onValueChange(newValue.take(maxLength))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                maxLength = maxLength,
                placeholder = "나를 소개하는 한마디를 작성해주세요!",
                borderColor = DduruTheme.colors.gray958.copy(alpha = 0.1f),
                textStyle = DduruTheme.typography.Body1,
                counterStyle = DduruTheme.typography.Body1,
                emptyCounterColor = DduruTheme.colors.gray700,
                contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 18.dp)
            )
        } else {
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
