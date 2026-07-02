package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = DduruTheme.typography.Title3,
        color = DduruTheme.colors.gray800,
        modifier = modifier.padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ChatSectionHeaderPreview() {
    DduruTheme {
        ChatSectionHeader(title = "대기중")
    }
}
