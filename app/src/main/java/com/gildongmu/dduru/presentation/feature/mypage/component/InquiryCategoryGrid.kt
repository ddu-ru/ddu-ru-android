package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.component.FilterChip
import com.gildongmu.dduru.presentation.feature.mypage.model.InquiryCategory

@Composable
fun InquiryCategoryGrid(
    selectedCategory: InquiryCategory?,
    onCategoryClick: (InquiryCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InquiryCategory.entries.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { category ->
                    FilterChip(
                        label = category.label,
                        isSelected = category == selectedCategory,
                        onClick = { onCategoryClick(category) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InquiryCategoryGridPreview() {
    DduruTheme {
        Box(modifier = Modifier.background(DduruTheme.colors.gray50)) {
            InquiryCategoryGrid(
                selectedCategory = InquiryCategory.SERVICE,
                onCategoryClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
