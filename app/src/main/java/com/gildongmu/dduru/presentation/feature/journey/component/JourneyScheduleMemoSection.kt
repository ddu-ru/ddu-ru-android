package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleMemoSection(
    memo: String,
    onMemoChange: (String) -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "메모",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray800
            )

            Text(
                text = " ×",
                modifier = Modifier.noRippleClickable(onClick = onRemoveClick),
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray400
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = memo,
            onValueChange = onMemoChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = DduruTheme.typography.Body1.copy(
                color = DduruTheme.colors.gray958
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 13.dp
                    )
                ) {
                    if (memo.isEmpty()) {
                        Text(
                            text = "메모를 입력해 주세요",
                            style = DduruTheme.typography.Body1,
                            color = DduruTheme.colors.gray400
                        )
                    }

                    innerTextField()
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DduruTheme.colors.gray200)
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleMemoSectionPreview() = DduruTheme {
    JourneyScheduleMemoSection(
        memo = "늦지 않게 도착하기",
        onMemoChange = {},
        onRemoveClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
