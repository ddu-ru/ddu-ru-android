package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatRoomSettingsCard(
    notificationsEnabled: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        modifier = modifier
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .background(DduruTheme.colors.gray0, shape)
            .padding(16.dp)
    ) {
        Text(
            text = "설정",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray800
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "채팅방 알림",
                style = DduruTheme.typography.Body3,
                color = DduruTheme.colors.gray800
            )
            ToggleSwitch(
                checked = notificationsEnabled,
                onCheckedChange = onNotificationsChange
            )
        }
    }
}

@Composable
private fun ToggleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val trackColor = if (checked) DduruTheme.colors.primary else DduruTheme.colors.gray200
    val paddingStart = if (checked) 23.dp else 2.dp
    val paddingEnd = if (checked) 2.dp else 23.dp

    Box(
        modifier = Modifier
            .size(width = 64.dp, height = 28.dp)
            .background(trackColor, RoundedCornerShape(100.dp))
            .padding(start = paddingStart, top = 2.dp, end = paddingEnd, bottom = 2.dp)
            .noRippleClickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .size(width = 39.dp, height = 24.dp)
                .background(DduruTheme.colors.gray0, RoundedCornerShape(100.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomSettingsCardOnPreview() {
    DduruTheme {
        ChatRoomSettingsCard(
            notificationsEnabled = true,
            onNotificationsChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomSettingsCardOffPreview() {
    DduruTheme {
        ChatRoomSettingsCard(
            notificationsEnabled = false,
            onNotificationsChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
