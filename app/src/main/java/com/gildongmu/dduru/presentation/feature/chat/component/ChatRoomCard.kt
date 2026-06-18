package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.color.Gray100
import com.gildongmu.dduru.core.designsystem.color.Gray500
import com.gildongmu.dduru.core.designsystem.color.Gray800
import com.gildongmu.dduru.core.designsystem.color.Secondary100
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.domain.entity.chat.ChatRoomEntity
import com.gildongmu.dduru.domain.entity.chat.ChatRoomType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatRoomCard(
    chatRoom: ChatRoomEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChatRoomAvatar(chatRoom = chatRoom)

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chatRoom.displayTitle,
                    modifier = Modifier.weight(1f),
                    style = DduruTheme.typography.Body2,
                    color = Gray800,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = chatRoom.lastMessageAt?.toDisplayTime() ?: "",
                    style = DduruTheme.typography.Caption2,
                    color = Gray500
                )
            }

            Text(
                text = chatRoom.lastMessage ?: "",
                style = DduruTheme.typography.Caption2,
                color = Gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (chatRoom.unreadCount > 0) {
            Spacer(modifier = Modifier.width(8.dp))
            UnreadBadge(count = chatRoom.unreadCount)
        }
    }
}

@Composable
private fun ChatRoomAvatar(
    chatRoom: ChatRoomEntity,
    modifier: Modifier = Modifier
) {
    val isGroup = chatRoom.roomType == ChatRoomType.GROUP
    val avatarShape = if (isGroup) RoundedCornerShape(16.dp) else CircleShape

    Box(modifier = modifier.size(if (isGroup) 54.dp else 48.dp)) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(avatarShape)
                .background(Gray100),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chatRoom.opponentNickname.take(1),
                style = DduruTheme.typography.Body1,
                color = Gray800
            )
        }

        if (isGroup) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Secondary100)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = DduruTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun UnreadBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(DduruTheme.colors.badgeOrange)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = DduruTheme.typography.Micro,
            color = DduruTheme.colors.gray0
        )
    }
}

private val ChatRoomEntity.displayTitle: String
    get() = when (roomType) {
        ChatRoomType.GROUP -> postTitle
        ChatRoomType.PRIVATE -> opponentNickname
    }

private fun LocalDateTime.toDisplayTime(): String {
    val now = LocalDateTime.now()
    return if (toLocalDate() == now.toLocalDate()) {
        format(DateTimeFormatter.ofPattern("HH:mm"))
    } else {
        format(DateTimeFormatter.ofPattern("MM/dd"))
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomCardPrivatePreview() {
    DduruTheme {
        ChatRoomCard(
            chatRoom = ChatRoomEntity(
                chatRoomId = 1L,
                roomType = ChatRoomType.PRIVATE,
                isActive = true,
                postTitle = "도쿄 여행 메이트 모집",
                opponentNickname = "여행좋아",
                journeyTitle = null,
                memberCount = 2,
                lastMessage = "네 좋아요! 같이 가요~",
                lastMessageAt = LocalDateTime.now(),
                unreadCount = 3
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomCardGroupPreview() {
    DduruTheme {
        ChatRoomCard(
            chatRoom = ChatRoomEntity(
                chatRoomId = 2L,
                roomType = ChatRoomType.GROUP,
                isActive = true,
                postTitle = "오사카 자유여행 동행 구해요",
                opponentNickname = "길동이",
                journeyTitle = "오사카 동행",
                memberCount = 5,
                lastMessage = "저도 갈게요!",
                lastMessageAt = LocalDateTime.now().minusDays(1),
                unreadCount = 0
            ),
            onClick = {}
        )
    }
}
