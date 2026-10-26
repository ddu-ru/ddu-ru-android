package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun HomeNotificationTopBar(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        HomeNotificationFloatingIconButton(
            iconRes = R.drawable.ic_chevron_left,
            contentDescription = "뒤로가기",
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = "알림",
            style = DduruTheme.typography.H1,
            color = DduruTheme.colors.black,
            modifier = Modifier.align(Alignment.Center)
        )

        HomeNotificationFloatingIconButton(
            iconRes = R.drawable.ic_more_vertical,
            contentDescription = "알림 메뉴",
            onClick = onMenuClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationTopBarPreview() {
    DduruTheme {
        HomeNotificationTopBar(
            onBackClick = {},
            onMenuClick = {},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Composable
private fun HomeNotificationFloatingIconButton(
    iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = CircleShape

    Box(
        modifier = modifier
            .size(48.dp)
            .dduruDualShadow(
                firstAlpha = 0.10f,
                secondAlpha = 0.08f,
                shape = shape
            )
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.92f),
                shape = shape
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.30f),
                shape = shape
            )
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = DduruTheme.colors.black,
            modifier = Modifier.size(24.dp)
        )
    }
}
