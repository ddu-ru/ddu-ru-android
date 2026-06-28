package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatFilterTab(
    label: String,
    selected: Boolean,
    unreadCount: Int = 0,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) DduruTheme.colors.black else DduruTheme.colors.gray100
    val textColor = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
    val shape = RoundedCornerShape(99.dp)

    val baseModifier = modifier
        .background(color = bgColor, shape = shape)
        .then(
            if (!selected) {
                Modifier.border(width = 1.dp, color = DduruTheme.colors.gray50, shape = shape)
            } else {
                Modifier
            }
        )
        .noRippleClickable(onClick = onClick)
        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)

    Row(
        modifier = baseModifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Caption1,
            color = textColor
        )
        if (unreadCount > 0) {
            UnreadBadge(count = unreadCount)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatFilterTabSelectedPreview() {
    DduruTheme {
        ChatFilterTab(label = "전체", selected = true, unreadCount = 3, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatFilterTabselectednumPreview() {
    DduruTheme {
        ChatFilterTab(label = "요청함", selected = false, unreadCount = 5, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatFilterTabunselectedPreview() {
    DduruTheme {
        ChatFilterTab(label = "신청내역", selected = false, unreadCount = 0, onClick = {})
    }
}
