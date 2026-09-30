package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
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
                .height(2.dp)
                .background(DduruTheme.colors.gray200)
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleTextFieldPreview() = DduruTheme {
    JourneyScheduleTextField(
        label = "제목",
        value = "",
        placeholder = "예: 시부야 스크램블 집합",
        onValueChange = {},
        modifier = Modifier.padding(16.dp)
    )
}
