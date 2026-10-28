package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.component.WritingButton

@Composable
fun JourneyWritingButton(
    onWritePostClick: () -> Unit,
    onWriteScheduleClick: () -> Unit,
    onWriteVoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    if (isExpanded) {
        JourneyWritingExpandedMenu(
            onDismiss = { isExpanded = false },
            onWritePostClick = {
                isExpanded = false
                onWritePostClick()
            },
            onWriteScheduleClick = {
                isExpanded = false
                onWriteScheduleClick()
            },
            onWriteVoteClick = {
                isExpanded = false
                onWriteVoteClick()
            },
            modifier = modifier
        )
    } else {
        WritingButton(
            onClick = { isExpanded = true },
            modifier = modifier.size(width = 108.dp, height = 42.dp)
        )
    }
}

@Composable
private fun JourneyWritingExpandedMenu(
    onDismiss: () -> Unit,
    onWritePostClick: () -> Unit,
    onWriteScheduleClick: () -> Unit,
    onWriteVoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .size(width = 135.dp, height = 144.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .border(1.dp, DduruTheme.colors.gray200, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(DduruTheme.colors.gray0)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            JourneyWritingMenuItem("게시글", onWritePostClick)
            JourneyWritingMenuItem("일정", onWriteScheduleClick)
            JourneyWritingMenuItem("투표", onWriteVoteClick)
        }
        Box(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(99.dp))
                .border(1.dp, DduruTheme.colors.gray200, RoundedCornerShape(99.dp))
                .clip(RoundedCornerShape(99.dp))
                .background(DduruTheme.colors.gray50)
                .clickable(onClick = onDismiss)
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "닫기",
                tint = DduruTheme.colors.gray700,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun JourneyWritingMenuItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_write_pencil),
            contentDescription = null,
            tint = DduruTheme.colors.gray700,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray700
        )
    }
}
