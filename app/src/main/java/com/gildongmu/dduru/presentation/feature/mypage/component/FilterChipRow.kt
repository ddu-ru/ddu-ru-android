package com.gildongmu.dduru.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
fun FilterChipRow(
    labels: List<String>,
    selectedIndex: Int,
    onChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        labels.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isSelected) DduruTheme.colors.black else DduruTheme.colors.gray50)
                    .then(
                        if (isSelected) {
                            Modifier
                        } else {
                            Modifier.border(width = 1.dp, color = DduruTheme.colors.gray100, shape = CircleShape)
                        }
                    )
                    .noRippleClickable { onChipClick(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = DduruTheme.typography.Body4,
                    color = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipRowPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            FilterChipRow(
                labels = listOf("전체", "서비스", "계정", "매칭", "보안"),
                selectedIndex = 1,
                onChipClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
