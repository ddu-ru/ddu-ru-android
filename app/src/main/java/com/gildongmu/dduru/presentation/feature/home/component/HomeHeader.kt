package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun HomeHeader(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            tint = DduruTheme.colors.primary
        )

        Spacer(modifier = Modifier.weight(1f))

        HomeHeaderIconButtons(
            onNotificationClick = onNotificationClick,
            onMenuClick = onMenuClick
        )
    }
}

@Composable
private fun HomeHeaderIconButtons(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = DduruTheme.colors.gray0,
                shape = shape
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_alram),
            contentDescription = null,
            tint = DduruTheme.colors.black,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onNotificationClick)
        )

        Icon(
            painter = painterResource(R.drawable.ic_list),
            contentDescription = null,
            tint = DduruTheme.colors.black,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onMenuClick)
        )
    }
}

@Preview(backgroundColor = 0xFFF5F6F8)
@Composable
private fun HomeHeader2Preview() {
    DduruTheme {
        HomeHeaderIconButtons(
            onNotificationClick = {},
            onMenuClick = {}
        )
    }
}

@Preview
@Composable
private fun HomeHeaderPreview() {
    DduruTheme {
        HomeHeader(
            onNotificationClick = {},
            onMenuClick = {}
        )
    }
}
