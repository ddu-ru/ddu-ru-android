package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType

@Composable
fun ChatRoomCard(
    displayName: String,
    postTitle: String?,
    participantCount: Int?,
    lastMessagePreview: String?,
    lastMessageAt: String?,
    unreadCount: Int,
    roomType: ChatRoomType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = DduruTheme.colors.gray200

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .drawBehind {
                val stroke = 1.dp.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = stroke
                )
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = stroke
                )
            }
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChatRoomAvatar(roomType = roomType)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ChatRoomNameRow(
                    displayName = displayName,
                    postTitle = postTitle,
                    participantCount = participantCount,
                    roomType = roomType
                )
                ChatRoomPreviewRow(
                    preview = lastMessagePreview,
                    time = lastMessageAt,
                    roomType = roomType
                )
            }
        }

        if (unreadCount > 0) {
            UnreadBadge(count = unreadCount)
        }
    }
}

@Composable
private fun ChatRoomNameRow(
    displayName: String,
    postTitle: String?,
    participantCount: Int?,
    roomType: ChatRoomType
) {
    when (roomType) {
        ChatRoomType.PRIVATE -> Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = displayName,
                modifier = Modifier.weight(1f, fill = false),
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray800,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (postTitle != null) {
                Text(
                    text = postTitle,
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        ChatRoomType.GROUP -> Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = displayName,
                modifier = Modifier.weight(1f, fill = false),
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray800,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (participantCount != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_people),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = DduruTheme.colors.gray500
                    )
                    Text(
                        text = participantCount.toString(),
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray500
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatRoomPreviewRow(
    preview: String?,
    time: String?,
    roomType: ChatRoomType
) {
    val previewColor = if (roomType == ChatRoomType.GROUP) {
        DduruTheme.colors.gray400
    } else {
        DduruTheme.colors.gray500
    }
    val timeColor = if (roomType == ChatRoomType.GROUP) {
        DduruTheme.colors.gray500
    } else {
        DduruTheme.colors.gray600
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = preview ?: "",
            modifier = Modifier.weight(1f, fill = false),
            style = DduruTheme.typography.Body2,
            color = previewColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (time != null) {
            if (!preview.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(DduruTheme.colors.gray500, CircleShape)
                )
            }
            Text(
                text = time,
                style = DduruTheme.typography.Body2,
                color = timeColor
            )
        }
    }
}

@Composable
private fun ChatRoomAvatar(roomType: ChatRoomType, modifier: Modifier = Modifier) {
    val isGroup = roomType == ChatRoomType.GROUP
    val avatarShape = if (isGroup) RoundedCornerShape(16.dp) else CircleShape

    Box(modifier = modifier.size(48.dp)) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(avatarShape)
                .background(DduruTheme.colors.gray100),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_home_people),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = DduruTheme.colors.gray400
            )
        }

        if (isGroup) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .background(DduruTheme.colors.gray50, RoundedCornerShape(12.dp))
                    .padding(3.dp)
                    .background(DduruTheme.colors.secondary100, RoundedCornerShape(9.dp))
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_home_people),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = DduruTheme.colors.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomCardPrivatePreview() {
    DduruTheme {
        ChatRoomCard(
            displayName = "username",
            postTitle = "제주 애월 2박 3일",
            participantCount = null,
            lastMessagePreview = "안녕하세요!",
            lastMessageAt = "방금",
            unreadCount = 10,
            roomType = ChatRoomType.PRIVATE,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomCardGroupPreview() {
    DduruTheme {
        ChatRoomCard(
            displayName = "제주 애월 2박 3일",
            postTitle = null,
            participantCount = 3,
            lastMessagePreview = "사진을 보냈습니다",
            lastMessageAt = "방금",
            unreadCount = 0,
            roomType = ChatRoomType.GROUP,
            onClick = {}
        )
    }
}
