package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun JourneyDetailPostItem(
    profileImageUrl: String?,
    username: String,
    createdAt: String,
    content: String,
    imageUrls: List<String>,
    commentCount: Int,
    isPinned: Boolean,
    onCommentClick: () -> Unit,
    onPinClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isPinned) {
                    DduruTheme.colors.secondary100
                } else {
                    DduruTheme.colors.gray0
                }
            )
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp,
            ),
    ) {
        AsyncImage(
            model = profileImageUrl ?: R.drawable.img_blank_profile,
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
                    text = username,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray846,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = createdAt,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 본문
            Text(
                text = content,
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.gray900,
            )

            if (imageUrls.isNotEmpty() || commentCount > 0) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    imageUrls.take(3).forEach { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    if (commentCount > 0) {
                        Text(
                            text = "${commentCount}개의 댓글",
                            style = DduruTheme.typography.Body2,
                            color = DduruTheme.colors.primary,
                            modifier = Modifier.clickable(
                                onClick = onCommentClick,
                            ),
                        )
                    }
                }
            }
        }

        // 우측 액션
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_comment_outline),
                contentDescription = "댓글",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onCommentClick),
                tint = DduruTheme.colors.gray300,
            )

            Icon(
                painter = painterResource(R.drawable.ic_pin_outline),
                contentDescription = "고정",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onPinClick),
                tint = if (isPinned) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray300
                },
            )

            Icon(
                painter = painterResource(R.drawable.ic_more_vertical),
                contentDescription = "더보기",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onMoreClick),
                tint = DduruTheme.colors.gray300,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyDetailPostItemPreview() {
    DduruTheme {
        JourneyDetailPostItem(
            profileImageUrl = null,
            username = "둘루",
            createdAt = "10분 전",
            content = "제주도 여행 일정 공유합니다! 맛집 추천도 편하게 남겨주세요.",
            imageUrls = emptyList(),
            commentCount = 12,
            isPinned = true,
            onCommentClick = {},
            onPinClick = {},
            onMoreClick = {},
        )
    }
}
