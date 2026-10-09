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
                shape = RoundedCornerShape(32.dp),
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(
                start = 20.dp,
                end = 6.dp,
                top = 6.dp,
                bottom = 6.dp,
            ),
        verticalAlignment = Alignment.Bottom,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 12.dp),
            textStyle = TextStyle(
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = Color.Black,
            ),
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
                            color = Color(0xFFD1D5DB),
                            fontSize = 15.sp,
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
                        Color(0xFF2D9CFF)
                    } else {
                        Color(0xFFE9EAED)
                    },
                    shape = RoundedCornerShape(50),
                ),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = "댓글 전송",
                tint = if (canSend) Color.White else Color(0xFFBFC3C9),
                modifier = Modifier.size(28.dp),
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
        value = "좋은 정보 감사합니다!",
        onValueChange = {},
        onSendClick = {},
        modifier = Modifier.padding(24.dp),
    )
}
