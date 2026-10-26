package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState

@Composable
fun MyPostCard(
    item: MatchingPostCardUiModel,
    showDivider: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
    showAuthor: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.gray0)
            .noRippleClickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyPostInfo(
                item = item,
                showAuthor = showAuthor,
                modifier = Modifier.weight(1f)
            )

            MyPostThumbnail(
                item = item,
                onLikeClick = onLikeClick
            )
        }

        if (showDivider) {
            HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray200)
        }
    }
}

@Composable
private fun myPostCaptionStyle() = DduruTheme.typography.Caption2.copy(lineHeight = 14.4.sp)

@Composable
private fun MyPostInfo(
    item: MatchingPostCardUiModel,
    showAuthor: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = item.title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                if (showAuthor) {
                    Text(
                        text = item.authorName,
                        style = myPostCaptionStyle(),
                        color = DduruTheme.colors.gray500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                MyPostMetaLine(item = item)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            MyPostStatusTag(
                text = item.gender.cardLabel,
                iconResList = when (item.gender) {
                    MatchingGender.MALE -> listOf(R.drawable.ic_men)
                    MatchingGender.FEMALE -> listOf(R.drawable.ic_woman)
                    MatchingGender.ANY -> listOf(R.drawable.ic_woman, R.drawable.ic_men)
                }
            )

            MyPostStatusTag(
                text = item.companionType.cardLabel,
                iconResList = listOf(
                    when (item.companionType) {
                        MatchingCompanionType.FULL -> R.drawable.ic_home_users
                        MatchingCompanionType.PARTIAL -> R.drawable.ic_home_calendar
                        MatchingCompanionType.MEAL -> R.drawable.ic_eat
                    }
                )
            )
        }
    }
}

@Composable
private fun MyPostMetaLine(
    item: MatchingPostCardUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f, fill = false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mappin),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(12.dp)
            )

            Text(
                text = item.location,
                style = myPostCaptionStyle(),
                color = DduruTheme.colors.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(2.dp)
                    .clip(CircleShape)
                    .background(DduruTheme.colors.gray500)
            )

            Text(
                text = item.dateRange,
                style = myPostCaptionStyle(),
                color = DduruTheme.colors.gray500,
                maxLines = 1
            )
        }

        Text(
            text = "${item.currentMemberCount}/${item.maxMemberCount}명",
            style = myPostCaptionStyle(),
            color = DduruTheme.colors.gray500,
            maxLines = 1
        )
    }
}

@Composable
private fun MyPostStatusTag(
    text: String,
    iconResList: List<Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray100)
            .padding(start = 8.dp, top = 3.dp, end = 8.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
            iconResList.forEach { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray500,
                    modifier = Modifier.size(10.dp)
                )
            }
        }

        Text(
            text = text,
            style = DduruTheme.typography.Micro2,
            color = DduruTheme.colors.gray500
        )
    }
}

@Composable
private fun MyPostThumbnail(
    item: MatchingPostCardUiModel,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = item.thumbnailImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 7.5.dp)
                .size(16.dp)
                .noRippleClickable(onClick = onLikeClick)
        ) {
            if (item.isLiked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_rounded_filled),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_rounded_filled),
                    contentDescription = null,
                    tint = DduruTheme.colors.black.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_rounded),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPostCardPreview() {
    DduruTheme {
        Column {
            MatchingUiState.fake.posts.take(3).forEachIndexed { index, post ->
                MyPostCard(
                    item = post.copy(isLiked = index == 1),
                    showDivider = index != 2,
                    onClick = {},
                    onLikeClick = {},
                    showAuthor = index != 0
                )
            }
        }
    }
}
