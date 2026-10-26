package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
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
fun HomeNotificationMoreMenu(
    onMarkAllAsReadClick: () -> Unit,
    isMarkingAllAsRead: Boolean,
    hasUnreadNotifications: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = modifier
            .width(135.dp)
            .dduruDualShadow(
                firstAlpha = 0.08f,
                secondAlpha = 0.06f,
                shape = shape
            )
            .background(
                color = DduruTheme.colors.gray0,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = shape
            )
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isMarkingAllAsRead) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = DduruTheme.colors.primary,
                    strokeWidth = 2.dp
                )
                Text(
                    text = "확인 처리 중",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray700
                )
            }
        } else {
            HomeNotificationMenuItem(
                iconRes = R.drawable.ic_check,
                text = if (hasUnreadNotifications) "전체 확인" else "모든 알림을 확인했어요",
                onClick = onMarkAllAsReadClick,
                enabled = hasUnreadNotifications
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun HomeNotificationMoreMenuPreview() {
    DduruTheme {
        Box(
            modifier = Modifier.padding(24.dp)
        ) {
            HomeNotificationMoreMenu(
                onMarkAllAsReadClick = {},
                isMarkingAllAsRead = false,
                hasUnreadNotifications = true
            )
        }
    }
}

@Composable
private fun HomeNotificationMenuItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .noRippleClickable(onClick = { if (enabled) onClick() })
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = if (enabled) DduruTheme.colors.gray700 else DduruTheme.colors.gray400,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = text,
            style = DduruTheme.typography.Caption2,
            color = if (enabled) DduruTheme.colors.gray700 else DduruTheme.colors.gray400,
            maxLines = 1
        )
    }
}
