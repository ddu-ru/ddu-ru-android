package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun UnreadBadge(count: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = DduruTheme.colors.badgeOrange,
                shape = RoundedCornerShape(99.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = DduruTheme.typography.Micro2,
            color = DduruTheme.colors.gray0
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnreadBadgePreview() {
    DduruTheme {
        UnreadBadge(count = 3)
    }
}

@Preview(showBackground = true)
@Composable
private fun UnreadBadgeOver99Preview() {
    DduruTheme {
        UnreadBadge(count = 100)
    }
}
