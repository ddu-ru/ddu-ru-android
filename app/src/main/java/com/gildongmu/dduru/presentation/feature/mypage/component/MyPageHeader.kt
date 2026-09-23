package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
fun MyPageHeader(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                tint = DduruTheme.colors.primary,
                modifier = Modifier
                    .width(46.dp)
                    .height(32.dp)
            )

            VerticalDivider(
                color = DduruTheme.colors.gray300,
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "마이페이지",
                style = DduruTheme.typography.H1,
                color = DduruTheme.colors.gray500
            )
        }

        MyPageActionBar(
            onNotificationClick = onNotificationClick,
            onMenuClick = onMenuClick
        )
    }
}

@Composable
private fun MyPageActionBar(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(99.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
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
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_alram),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onNotificationClick),
            tint = DduruTheme.colors.black
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_list),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onMenuClick),
            tint = DduruTheme.colors.black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageHeaderPreview() {
    DduruTheme {
        MyPageHeader(
            onNotificationClick = {},
            onMenuClick = {}
        )
    }
}
