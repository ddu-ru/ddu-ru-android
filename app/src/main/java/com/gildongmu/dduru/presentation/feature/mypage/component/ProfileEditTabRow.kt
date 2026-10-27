package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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

val ProfileEditTabTitles = listOf("내 정보", "가고싶은 여행", "나의 여행 스타일")

@Composable
fun ProfileEditTabRow(
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabs: List<String> = ProfileEditTabTitles
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabs.forEachIndexed { index, title ->
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
                    .noRippleClickable { onTabClick(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.Body2,
                    color = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditTabRowPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            ProfileEditTabRow(
                selectedIndex = 2,
                onTabClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
