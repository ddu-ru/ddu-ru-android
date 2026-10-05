package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class JourneyDetailFilterType(val label: String?) {
    FIXED(null),
    NOTICE("공지"),
    BOARD("게시판"),
    SCHEDULE("일정"),
    PHOTO("사진"),
    VOTE("투표")
}

@Composable
fun JourneyDetailFilterSection(
    filters: ImmutableList<JourneyDetailFilterType>,
    selectedFilter: JourneyDetailFilterType,
    onFilterClick: (JourneyDetailFilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEach { filter ->
            JourneyDetailFilterItem(
                type = filter,
                isSelected = selectedFilter == filter,
                onClick = {
                    onFilterClick(filter)
                }
            )
        }
    }
}

@Composable
private fun JourneyDetailFilterItem(
    type: JourneyDetailFilterType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
    val itemModifier = modifier
        .background(
            color = if (isSelected) DduruTheme.colors.black else DduruTheme.colors.gray50,
            shape = RoundedCornerShape(99.dp)
        )
        .then(
            if (isSelected) {
                Modifier
            } else {
                Modifier.border(
                    width = 1.dp,
                    color = DduruTheme.colors.gray50,
                    shape = RoundedCornerShape(99.dp)
                )
            }
        )
        .noRippleClickable(onClick = onClick)
        .padding(
            horizontal = 16.dp,
            vertical = 8.dp
        )

    if (type == JourneyDetailFilterType.FIXED) {
        Icon(
            painter = painterResource(R.drawable.ic_filter_fixed),
            contentDescription = null,
            tint = contentColor,
            modifier = itemModifier.size(16.dp)
        )
    } else {
        Text(
            text = requireNotNull(type.label),
            style = DduruTheme.typography.Body2,
            color = contentColor,
            modifier = itemModifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyDetailFilterSectionPreview() {
    DduruTheme {
        JourneyDetailFilterSection(
            filters = persistentListOf(
                JourneyDetailFilterType.FIXED,
                JourneyDetailFilterType.NOTICE,
                JourneyDetailFilterType.BOARD,
                JourneyDetailFilterType.SCHEDULE,
                JourneyDetailFilterType.PHOTO,
                JourneyDetailFilterType.VOTE
            ),
            selectedFilter = JourneyDetailFilterType.NOTICE,
            onFilterClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
