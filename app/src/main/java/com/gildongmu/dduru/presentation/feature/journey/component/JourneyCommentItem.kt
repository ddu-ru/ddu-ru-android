package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R

data class JourneyCommentItemUiModel(
    val id: Long,
    val username: String,
    val createdAt: String,
    val content: String,
    val isPinned: Boolean = false,
)

@Composable
fun JourneyCommentItem(
    comment: JourneyCommentItemUiModel,
    onReplyClick: () -> Unit,
    onPinClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFF0F0F0), CircleShape),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = comment.username,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF303030),
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = comment.createdAt,
                    fontSize = 13.sp,
                    color = Color(0xFF9CA3AF),
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = comment.content,
                fontSize = 14.sp,
                color = Color(0xFF303030),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = null,
                tint = Color(0xFFBDC1C6),
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = onReplyClick),
            )

            Icon(
                imageVector = Icons.Outlined.PushPin,
                contentDescription = "고정",
                tint = if (comment.isPinned) {
                    Color(0xFF2D9CFF)
                } else {
                    Color(0xFFBDC1C6)
                },
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = onPinClick),
            )

            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "더보기",
                tint = Color(0xFFBDC1C6),
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = onMoreClick),
            )
        }
    }
}

@Preview(name = "일반 댓글", showBackground = true, widthDp = 375)
@Composable
private fun JourneyCommentItemPreview() {
    JourneyCommentItem(
        comment = JourneyCommentItemUiModel(
            id = 1L,
            username = "길동무",
            createdAt = "4월 25일 12:23",
            content = "여행 정보가 정말 유용하네요. 감사합니다!",
        ),
        onReplyClick = {},
        onPinClick = {},
        onMoreClick = {},
    )
}

@Preview(name = "고정 댓글", showBackground = true, widthDp = 375)
@Composable
private fun JourneyPinnedCommentItemPreview() {
    JourneyCommentItem(
        comment = JourneyCommentItemUiModel(
            id = 2L,
            username = "여행지기",
            createdAt = "4월 25일 12:23",
            content = "모임 장소는 시청역 3번 출구 앞입니다.",
            isPinned = true,
        ),
        onReplyClick = {},
        onPinClick = {},
        onMoreClick = {},
    )
}
