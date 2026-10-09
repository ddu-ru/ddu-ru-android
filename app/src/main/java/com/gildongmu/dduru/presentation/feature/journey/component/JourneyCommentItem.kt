package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyCommentItemUiState

@Composable
fun JourneyCommentItem(
    comment: JourneyCommentItemUiState,
    onReplyClick: () -> Unit,
    onPinClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        AsyncImage(
            model = comment.profileImageUrl ?: R.drawable.img_blank_profile,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = comment.username,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray846,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = comment.createdAt,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500,
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = comment.content,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray846,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bottom_nav_chat),
                contentDescription = null,
                tint = DduruTheme.colors.gray400,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onReplyClick),
            )

            Icon(
                painter = painterResource(R.drawable.ic_filter_fixed2),
                contentDescription = null,
                tint = if (comment.isPinned) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray400
                },
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onPinClick),
            )

            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = null,
                tint = DduruTheme.colors.gray400,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onMoreClick),
            )
        }
    }
}

@Preview(name = "일반 댓글", showBackground = true, widthDp = 375)
@Composable
private fun JourneyCommentItemPreview() {
    JourneyCommentItem(
        comment = JourneyCommentItemUiState(
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
        comment = JourneyCommentItemUiState(
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
