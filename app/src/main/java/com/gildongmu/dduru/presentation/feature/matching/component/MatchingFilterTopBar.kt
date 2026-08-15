package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingFilterHeader(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "필터 설정",
            style = DduruTheme.typography.Title1,
            color = DduruTheme.colors.black
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_x_gray),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable(onClick = onClose)
        )
    }
}

@Composable
fun MatchingFilterTabRow(
    selectedTab: MatchingFilterTab,
    onTabSelected: (MatchingFilterTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MatchingFilterPill(
            text = MatchingFilterTab.DATE.label,
            selected = selectedTab == MatchingFilterTab.DATE,
            onClick = { onTabSelected(MatchingFilterTab.DATE) }
        )
        MatchingFilterPill(
            text = MatchingFilterTab.DETAIL.label,
            selected = selectedTab == MatchingFilterTab.DETAIL,
            onClick = { onTabSelected(MatchingFilterTab.DETAIL) }
        )
    }
}

@Composable
private fun MatchingFilterPill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(if (selected) DduruTheme.colors.black else DduruTheme.colors.gray50)
            .then(
                if (selected) {
                    Modifier
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = DduruTheme.colors.gray100,
                        shape = RoundedCornerShape(99.dp)
                    )
                }
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Body2,
            color = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
        )
    }
}
