package com.gildongmu.dduru.presentation.feature.register.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun ProfileIntroduction(
    text: String,
    maxLength: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp, bottom = 16.dp),
            text = "자기소개",
            style = DduruTheme.typography.BodyLarge
        )
        Box(
            modifier = Modifier
                .border(1.dp, DduruTheme.colors.Gray300, RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                maxLines = 4,
                textStyle = DduruTheme.typography.BodyLarge.copy(
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier.padding(16.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "나를 소개하는 한마디를 작성해주세요!",
                                style = DduruTheme.typography.BodyLarge,
                                color = DduruTheme.colors.Gray300
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
                style = DduruTheme.typography.BodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = DduruTheme.colors.Gray300
            )
        }
    }
}
