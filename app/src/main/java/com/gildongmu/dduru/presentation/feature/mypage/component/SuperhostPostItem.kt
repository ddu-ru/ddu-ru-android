package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostPostUiModel

@Composable
fun SuperhostPostItem(
    post: SuperhostPostUiModel,
    isApplied: Boolean,
    isActionEnabled: Boolean,
    showDivider: Boolean,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                PostThumbnail(thumbnailUrl = post.thumbnailUrl)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    PostTitleAndMeta(
                        title = post.title,
                        destination = post.destination,
                        dateRange = post.dateRange,
                        capacity = post.capacity
                    )

                    if (isApplied) {
                        AppliedPostFooter(
                            remainingTime = post.remainingTime.orEmpty(),
                            viewCount = post.viewCount
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                        ) {
                            PostCount(iconRes = R.drawable.ic_post_eye, count = post.viewCount)
                        }
                    }
                }
            }

            SuperhostActionButton(
                text = if (isApplied) "연장하기" else "적용하기",
                isApplied = isApplied,
                enabled = isActionEnabled,
                onClick = onActionClick
            )
        }

        if (showDivider) {
            HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray200)
        }
    }
}

@Composable
private fun AppliedPostFooter(
    remainingTime: String,
    viewCount: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(DduruTheme.colors.primary, CircleShape)
                        .padding(2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_superhost),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray0,
                        modifier = Modifier.size(6.dp)
                    )
                }
                Text(
                    text = "슈퍼호스트 적용중",
                    style = DduruTheme.typography.Micro2,
                    color = DduruTheme.colors.gray600,
                    modifier = Modifier.alpha(0.7f)
                )
            }
            Text(
                text = remainingTime,
                style = postCaptionStyle(),
                color = DduruTheme.colors.primary
            )
        }

        PostCount(iconRes = R.drawable.ic_post_eye, count = viewCount)
    }
}

@Composable
private fun SuperhostActionButton(
    text: String,
    isApplied: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    val backgroundColor = when {
        !enabled -> DduruTheme.colors.gray200
        isApplied -> DduruTheme.colors.secondary100
        else -> DduruTheme.colors.primary
    }
    val textColor = when {
        !enabled -> DduruTheme.colors.gray400
        isApplied -> DduruTheme.colors.primary
        else -> DduruTheme.colors.gray0
    }
    val borderColor = when {
        !enabled -> DduruTheme.colors.gray300
        isApplied -> DduruTheme.colors.secondary100
        else -> DduruTheme.colors.gray0.copy(alpha = 0.3f)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(44.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, borderColor, shape)
            .then(if (enabled) Modifier.noRippleClickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = text,
            style = DduruTheme.typography.Title2,
            color = textColor
        )
    }
}

@Composable
internal fun PostThumbnail(
    thumbnailUrl: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray100)
    )
}

@Composable
internal fun PostTitleAndMeta(
    title: String,
    destination: String,
    dateRange: String,
    capacity: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = DduruTheme.typography.Title3,
            color = DduruTheme.colors.black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mappin),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray500,
                    modifier = Modifier.size(12.dp)
                )
                PostMetaText(text = destination)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(2.dp)
                        .background(DduruTheme.colors.gray500, CircleShape)
                )
                PostMetaText(text = dateRange)
            }

            PostMetaText(text = capacity)
        }
    }
}

@Composable
private fun PostMetaText(text: String) {
    Text(
        text = text,
        style = postCaptionStyle(),
        color = DduruTheme.colors.gray500,
        maxLines = 1
    )
}

@Composable
internal fun PostCount(
    iconRes: Int,
    count: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = count,
            style = postCaptionStyle(),
            color = DduruTheme.colors.gray500
        )
    }
}

@Composable
private fun postCaptionStyle() = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp)

private val PreviewPost = SuperhostPostUiModel(
    id = 1,
    title = "제주도 3박 4일 여행",
    destination = "제주도 한라산",
    dateRange = "03.15 - 03.18",
    capacity = "3/4명",
    viewCount = "344",
    remainingTime = "2일 2시간 23분 남음"
)

@Preview(showBackground = true)
@Composable
private fun SuperhostPostItemPreview() {
    DduruTheme {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SuperhostPostItem(
                post = PreviewPost,
                isApplied = true,
                isActionEnabled = true,
                showDivider = true,
                onActionClick = {}
            )
            SuperhostPostItem(
                post = PreviewPost,
                isApplied = false,
                isActionEnabled = true,
                showDivider = true,
                onActionClick = {}
            )
            SuperhostPostItem(
                post = PreviewPost,
                isApplied = false,
                isActionEnabled = false,
                showDivider = false,
                onActionClick = {}
            )
        }
    }
}
