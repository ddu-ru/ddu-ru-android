package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.MAX_POST_LENGTH

@Composable
fun JourneyPostContentField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 17.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = DduruTheme.typography.Body2.copy(
                color = DduruTheme.colors.gray958
            ),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = "내용을 자유롭게 작성해 보세요",
                            style = DduruTheme.typography.Body2,
                            color = DduruTheme.colors.gray400
                        )
                    }

                    innerTextField()
                }
            }
        )

        Text(
            text = "${value.length}/$MAX_POST_LENGTH",
            modifier = Modifier.align(Alignment.BottomEnd),
            style = DduruTheme.typography.Caption,
            color = if (isError) {
                DduruTheme.colors.validationRed
            } else {
                DduruTheme.colors.gray400
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyPostContentFieldPreview() {
    DduruTheme {
        JourneyPostContentField(
            value = "제주에서 보낸 첫날, 정말 즐거웠어요!",
            onValueChange = {},
            isError = false
        )
    }
}
