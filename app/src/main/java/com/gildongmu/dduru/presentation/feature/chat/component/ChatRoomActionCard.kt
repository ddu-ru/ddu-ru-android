package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatRoomActionCard(
    onReportClick: () -> Unit,
    onLeaveClick: () -> Unit,
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
        ChatRoomActionItem(
            iconRes = R.drawable.ic_siren,
            label = "신고하기",
            onClick = onReportClick
        )
        ChatRoomActionItem(
            iconRes = R.drawable.ic_signout,
            label = "채팅방 나가기",
            onClick = onLeaveClick
        )
    }
}

@Composable
private fun ChatRoomActionItem(
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = DduruTheme.colors.validationRed
        )
        Text(
            text = label,
            style = DduruTheme.typography.Body3,
            color = DduruTheme.colors.validationRed
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomActionCardPreview() {
    DduruTheme {
        ChatRoomActionCard(
            onReportClick = {},
            onLeaveClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
