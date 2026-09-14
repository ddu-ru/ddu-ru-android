package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyScheduleOptionSection(
    showMemoOption: Boolean,
    showCategoryOption: Boolean,
    onMemoClick: () -> Unit,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "옵션 추가",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray800
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (showMemoOption) {
                JourneyScheduleOptionChip(
                    icon = painterResource(id = R.drawable.ic_journey_memo),
                    text = "메모",
                    onClick = onMemoClick
                )
            }

            if (showCategoryOption) {
                JourneyScheduleOptionChip(
                    icon = painterResource(id = R.drawable.ic_journey_tag),
                    text = "카테고리",
                    onClick = onCategoryClick
                )
            }
        }
    }
}

@Composable
private fun JourneyScheduleOptionChip(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray200)
            .noRippleClickable(onClick = onClick)
            .padding( 12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = DduruTheme.colors.gray700
        )
        Text(
            text = text,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray700
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyScheduleOptionSectionPreview() = DduruTheme {
    JourneyScheduleOptionSection(
        showMemoOption = true,
        showCategoryOption = true,
        onMemoClick = {},
        onCategoryClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
