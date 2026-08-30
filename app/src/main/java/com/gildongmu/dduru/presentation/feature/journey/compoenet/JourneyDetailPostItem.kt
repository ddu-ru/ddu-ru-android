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
    title: String,
    imageUrls: List<String>,
    commentCount: Int,
    isPinned: Boolean,
    onCommentClick: () -> Unit,
    onPinClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isPinned) {
                    DduruTheme.colors.pinnedPostBackground
                } else {
                    DduruTheme.colors.gray0
                }
            )
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        AsyncImage(
            model = profileImageUrl ?: R.drawable.img_blank_profile,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = username,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray846
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = createdAt,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray846
            )

            if (imageUrls.isNotEmpty() || commentCount > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    imageUrls.take(3).forEach { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (commentCount > 0) {
                        Text(
                            text = "${commentCount}개의 댓글",
                            style = DduruTheme.typography.Caption2,
                            color = DduruTheme.colors.primary,
                            modifier = Modifier.clickable(
                                onClick = onCommentClick
                            )
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bottom_nav_chat),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onCommentClick),
                tint = DduruTheme.colors.gray400
            )

            Icon(
                painter = painterResource(R.drawable.ic_filter_fixed2),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onPinClick),
                tint = if (isPinned) {
                    DduruTheme.colors.primary
                } else {
                    DduruTheme.colors.gray400
                }
            )

            Icon(
                painter = painterResource(R.drawable.ic_more_vertical),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onMoreClick),
                tint = DduruTheme.colors.gray400
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
            title = "제주도 여행 일정 공유합니다! 맛집 추천도 편하게 남겨주세요.",
            imageUrls = emptyList(),
            commentCount = 12,
            isPinned = true,
            onCommentClick = {},
            onPinClick = {},
            onMoreClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyDetailUnpinnedPostItemPreview() {
    DduruTheme {
        JourneyDetailPostItem(
            profileImageUrl = null,
            username = "둘루",
            createdAt = "10분 전",
            title = "제주도 여행 일정 공유합니다! 맛집 추천도 편하게 남겨주세요.",
            imageUrls = emptyList(),
            commentCount = 12,
            isPinned = false,
            onCommentClick = {},
            onPinClick = {},
            onMoreClick = {}
        )
    }
}
