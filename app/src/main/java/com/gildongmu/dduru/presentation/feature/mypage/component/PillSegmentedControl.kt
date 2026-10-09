package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PillSegmentedControl(
    tabs: List<String>,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = CircleShape)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 40.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) DduruTheme.colors.black else DduruTheme.colors.gray50)
                    .noRippleClickable { onTabClick(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.Body4,
                    color = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PillSegmentedControlPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            PillSegmentedControl(
                tabs = listOf("FAQ", "최근 문의"),
                selectedIndex = 0,
                onTabClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
