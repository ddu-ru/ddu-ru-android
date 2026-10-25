package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyDetailActionBar(
    onNotificationClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.08f,
                shape = shape
            )
            .clip(shape)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = shape
            )
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.50f),
                shape = shape
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
    ) {
        JourneyDetailActionIcon(
            iconRes = R.drawable.ic_alram,
            onClick = onNotificationClick
        )
        JourneyDetailActionIcon(
            iconRes = R.drawable.ic_share,
            onClick = onShareClick
        )
        JourneyDetailActionIcon(
            iconRes = R.drawable.ic_more_vertical,
            onClick = onMoreClick
        )
    }
}

@Composable
private fun JourneyDetailActionIcon(
    iconRes: Int,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
            .noRippleClickable(onClick = onClick),
        tint = DduruTheme.colors.black
    )
}

@Preview(showBackground = true)
@Composable
private fun JourneyDetailActionBarPreview() {
    DduruTheme {
        JourneyDetailActionBar(
            onNotificationClick = {},
            onShareClick = {},
            onMoreClick = {}
        )
    }
}
