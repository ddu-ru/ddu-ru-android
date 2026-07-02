package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MessageBubble(
    message: String,
    time: String,
    unreadCount: Int,
    isMine: Boolean,
    isHead: Boolean,
    modifier: Modifier = Modifier
) {
    val bubbleShape = if (isMine) {
        RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp,
            bottomEnd = 8.dp
        )
    } else {
        if (isHead) {
            RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 8.dp,
                bottomEnd = 16.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        }
    }

    Row(
        horizontalArrangement = if (isMine) {
            Arrangement.spacedBy(8.dp, Alignment.End)
        } else {
            Arrangement.spacedBy(8.dp, Alignment.Start)
        },
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isMine) {
            if (time.isNotEmpty()) {
                MessageTimeInfo(
                    time = time,
                    unreadCount = unreadCount,
                    isMine = true
                )
            }
        }

        Text(
            text = message,
            style = DduruTheme.typography.Body2,
            color = if (isMine) DduruTheme.colors.gray0 else DduruTheme.colors.gray846,
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    shape = bubbleShape,
                    spotColor = DduruTheme.colors.black.copy(alpha = 0.12f),
                    ambientColor = DduruTheme.colors.black.copy(alpha = 0.12f)
                )
                .widthIn(max = if (isMine) 244.dp else 220.dp)
                .background(
                    color = if (isMine) DduruTheme.colors.primary else DduruTheme.colors.gray0,
                    shape = bubbleShape
                )
                .padding(12.dp)
        )

        if (!isMine) {
            if (time.isNotEmpty()) {
                MessageTimeInfo(
                    time = time,
                    unreadCount = unreadCount,
                    isMine = false
                )
            }
        }
    }
}

@Composable
private fun MessageTimeInfo(
    time: String,
    unreadCount: Int,
    isMine: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (isMine && unreadCount > 0) {
            Text(
                text = unreadCount.toString(),
                style = DduruTheme.typography.Micro3,
                color = DduruTheme.colors.gray500
            )
        }
        Text(
            text = time,
            style = DduruTheme.typography.Micro3,
            color = DduruTheme.colors.gray400
        )
        if (!isMine && unreadCount > 0) {
            Text(
                text = unreadCount.toString(),
                style = DduruTheme.typography.Micro3,
                color = DduruTheme.colors.gray500
            )
        }
    }
}

@Composable
fun GroupMessageBubble(
    username: String,
    message: String,
    time: String,
    unreadCount: Int,
    isHead: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isHead) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DduruTheme.colors.gray100)
            )
        } else {
            Box(modifier = Modifier.size(32.dp))
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            if (isHead) {
                Text(
                    text = username,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray846
                )
            }
            MessageBubble(
                message = message,
                time = time,
                unreadCount = unreadCount,
                isMine = false,
                isHead = isHead
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleMinePreview() {
    DduruTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            MessageBubble(
                message = "시간 표시 안됨",
                time = "",
                unreadCount = 0,
                isMine = true,
                isHead = true
            )
            MessageBubble(
                message = "일단 기준은 220으로. 그래서 길어지면 이정도로 표시됩니다.",
                time = "15:22",
                unreadCount = 1,
                isMine = true,
                isHead = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageBubbleOtherPreview() {
    DduruTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            MessageBubble(
                message = "시간 표시 안됨",
                time = "",
                unreadCount = 0,
                isMine = false,
                isHead = true
            )
            MessageBubble(
                message = "tail은 이렇게",
                time = "15:22",
                unreadCount = 1,
                isMine = false,
                isHead = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupMessageBubblePreview() {
    DduruTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            GroupMessageBubble(
                username = "username",
                message = "시간 표시 안됨",
                time = "",
                unreadCount = 0,
                isHead = true
            )
            GroupMessageBubble(
                username = "username",
                message = "tail은 이렇게",
                time = "15:22",
                unreadCount = 1,
                isHead = false
            )
        }
    }
}
