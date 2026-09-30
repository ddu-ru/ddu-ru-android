package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyScheduleCategory

@Composable
fun JourneyScheduleCategorySection(
    selectedCategory: JourneyScheduleCategory?,
    onCategoryClick: (JourneyScheduleCategory) -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "카테고리",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray800
            )

            Text(
                text = " ×",
                modifier = Modifier.noRippleClickable(onClick = onRemoveClick),
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray400
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JourneyScheduleCategory.entries.forEach { category ->
                val selected = selectedCategory == category

                Text(
                    text = "${category.emoji} ${category.label}",
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .background(
                            if (selected) {
                                DduruTheme.colors.secondary100
                            } else {
                                DduruTheme.colors.gray100
                            }
                        )
                        .noRippleClickable {
                            onCategoryClick(category)
                        }
                        .padding(
                            horizontal = 12.dp,
                            vertical = 12.dp
                        ),
                    style = DduruTheme.typography.Caption1,
                    color = if (selected) {
                        DduruTheme.colors.primary
                    } else {
                        DduruTheme.colors.gray700
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleCategorySectionPreview() = DduruTheme {
    JourneyScheduleCategorySection(
        selectedCategory = JourneyScheduleCategory.TOUR,
        onCategoryClick = {},
        onRemoveClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
