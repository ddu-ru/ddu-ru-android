package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyCommentInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val canSend = value.isNotBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(99.dp),
            )
            .background(
                color = DduruTheme.colors.gray0,
                shape = RoundedCornerShape(99.dp),
            )
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            textStyle = DduruTheme.typography.Body2,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
            ),
            minLines = 1,
            maxLines = 5,
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = "답글 달기",
                            style = DduruTheme.typography.Body2,
                            color = DduruTheme.colors.gray300,
                        )
                    }

                    innerTextField()
                }
            },
        )

        IconButton(
            onClick = onSendClick,
            enabled = canSend,
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = if (canSend) {
                        DduruTheme.colors.primary
                    } else {
                        DduruTheme.colors.gray200
                    },
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(10.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = null,
                tint = if (canSend) DduruTheme.colors.gray0 else DduruTheme.colors.gray400,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(name = "댓글 입력 - 비활성", showBackground = true, widthDp = 375)
@Composable
private fun JourneyCommentInputEmptyPreview() {
    JourneyCommentInput(
        value = "",
        onValueChange = {},
        onSendClick = {},
        modifier = Modifier.padding(24.dp),
    )
}

@Preview(name = "댓글 입력 - 활성", showBackground = true, widthDp = 375)
@Composable
private fun JourneyCommentInputFilledPreview() {
    JourneyCommentInput(
        value = "좋은 정보 감사합니다!\n좋은 정보 감사합니다!",
        onValueChange = {},
        onSendClick = {},
        modifier = Modifier.padding(24.dp),
    )
}
