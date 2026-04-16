package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

const val PROFILE_INTRODUCTION_MAX_LENGTH = 60

@Composable
fun ProfileIntroduction(
    text: String,
    modifier: Modifier = Modifier,
    maxLength: Int = PROFILE_INTRODUCTION_MAX_LENGTH,
    onValueChange: (String) -> Unit,
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

        Box(
            modifier = Modifier
                .border(
                    1.dp,
                    DduruTheme.colors.gray958.copy(alpha = 0.1f),
                    RoundedCornerShape(16.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newValue ->
                    onValueChange(newValue.take(maxLength))
                },
                maxLines = 4,
                textStyle = DduruTheme.typography.Body1.copy(
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier.padding(16.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "나를 소개하는 한마디를 작성해주세요!",
                                style = DduruTheme.typography.Body1,
                                color = DduruTheme.colors.gray300
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 140.dp)
            )
            Text(
                text = "${text.length} / $maxLength",
                style = DduruTheme.typography.Body1,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = DduruTheme.colors.gray300
            )
        }
    }
}
