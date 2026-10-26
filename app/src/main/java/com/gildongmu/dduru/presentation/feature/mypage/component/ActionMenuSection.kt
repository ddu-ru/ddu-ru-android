package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class ActionMenuItem(
    val iconRes: Int,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun ActionMenuSection(
    items: List<ActionMenuItem>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = item.onClick),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Icon(
                    painter = painterResource(item.iconRes),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray800,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = item.label,
                    color = DduruTheme.colors.gray800,
                    style = DduruTheme.typography.Title3
                )
            }

            if (index != items.lastIndex) {
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    color = DduruTheme.colors.gray200,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Preview(name = "ActionMenuSection")
@Composable
private fun ActionMenuSectionPreview() {
    DduruTheme {
        ActionMenuSection(
            items = listOf(
                ActionMenuItem(iconRes = R.drawable.ic_heart_rounded, label = "찜한 여행", onClick = {}),
                ActionMenuItem(iconRes = R.drawable.ic_pencil, label = "작성글 관리", onClick = {}),
                ActionMenuItem(iconRes = R.drawable.ic_prohibit, label = "차단 목록", onClick = {})
            )
        )
    }
}
