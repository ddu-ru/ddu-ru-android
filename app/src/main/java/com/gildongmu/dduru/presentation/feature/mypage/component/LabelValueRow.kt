package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun LabelValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray500
        )
        Text(
            text = value,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray900
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelValueRowPreview() {
    DduruTheme {
        LabelValueRow(label = "생년월일", value = "1998.03.14")
    }
}
