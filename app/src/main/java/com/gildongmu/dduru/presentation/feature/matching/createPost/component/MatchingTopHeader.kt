package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingTopHeader(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 46.dp, height = 32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Spacer(
                modifier = Modifier
                    .background(DduruTheme.colors.gray300)
                    .padding(start = 1.dp, top = 8.25.dp, bottom = 8.25.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "매칭",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray500
            )
        }

        MatchingHeaderActions(
            onNotificationClick = onNotificationClick,
            onMenuClick = onMenuClick
        )
    }
}

@Composable
private fun MatchingHeaderActions(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Row(
        modifier = modifier
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.08f,
                shape = shape,
                firstElevation = 16.dp,
                secondElevation = 8.dp
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                shape = shape
            )
            .width(96.dp)
            .height(48.dp)
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.5f),
                shape = shape
            )
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_alram),
            contentDescription = null,
            tint = DduruTheme.colors.black,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onNotificationClick)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            tint = DduruTheme.colors.black,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onMenuClick)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun MatchingTopHeaderPreview() {
    DduruTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            MatchingTopHeader(
                onNotificationClick = {},
                onMenuClick = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun MatchingHeaderActionsPreview() {
    DduruTheme {
        Box(
            modifier = Modifier.padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            MatchingHeaderActions(
                onNotificationClick = {},
                onMenuClick = {}
            )
        }
    }
}
