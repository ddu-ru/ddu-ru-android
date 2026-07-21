package com.gildongmu.dduru.presentation.feature.matching.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingCompanionType
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingGender
import com.gildongmu.dduru.presentation.feature.matching.model.MatchingPostCardUiModel
import com.gildongmu.dduru.presentation.feature.matching.state.MatchingUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MatchingPostListSection(
    items: ImmutableList<MatchingPostCardUiModel>,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.id },
            contentType = { _, _ -> MatchingPostCardUiModel::class }
        ) { index, item ->
            val isFirstItem = index == 0
            val isLastItem = index == items.lastIndex

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = if (isFirstItem) 16.dp else 0.dp,
                            topEnd = if (isFirstItem) 16.dp else 0.dp,
                            bottomStart = if (isLastItem) 16.dp else 0.dp,
                            bottomEnd = if (isLastItem) 16.dp else 0.dp
                        )
                    )
                    .background(DduruTheme.colors.gray0)
            ) {
                MatchingPostCard(
                    item = item,
                    showDivider = !isLastItem,
                    onClick = { onCardClick(item.id) },
                    onLikeClick = { onLikeClick(item.id) }
                )
            }
        }
    }
}

@Composable
private fun MatchingPostCard(
    item: MatchingPostCardUiModel,
    showDivider: Boolean,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
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
            MatchingPostInfo(
                item = item,
                modifier = Modifier.weight(1f)
            )

            MatchingPostThumbnail(
                item = item,
                onLikeClick = onLikeClick
            )
        }

        if (showDivider) {
            MatchingPostDivider()
        }
    }
}

@Composable
private fun MatchingPostDivider(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.gray200)
            .padding(top = 1.dp)
    )
}

@Composable
private fun MatchingPostInfo(
    item: MatchingPostCardUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = item.title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            MatchingAuthorLine(item = item)

            MatchingMetaLine(item = item)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MatchingStatusTag(
                text = item.gender.label,
                iconRes = R.drawable.ic_home_person
            )

            MatchingStatusTag(
                text = item.companionType.label,
                iconRes = when (item.companionType) {
                    MatchingCompanionType.Full -> R.drawable.ic_home_users
                    MatchingCompanionType.Partial -> R.drawable.ic_home_calendar
                    MatchingCompanionType.Food -> R.drawable.ic_hashtag
                }
            )
        }
    }
}

@Composable
private fun MatchingAuthorLine(
    item: MatchingPostCardUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = item.authorName,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (item.isSuperHost) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(DduruTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_superhost),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray0,
                        modifier = Modifier.size(6.dp)
                    )
                }

                Text(
                    text = "슈퍼호스트",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun MatchingMetaLine(
    item: MatchingPostCardUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home_location),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier.size(12.dp)
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = item.location,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        MatchingMetaDot()

        Text(
            text = item.dateRange,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500,
            maxLines = 1
        )

        MatchingMetaDot()

        Text(
            text = "${item.currentMemberCount}/${item.maxMemberCount}명",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500,
            maxLines = 1
        )
    }
}

@Composable
private fun MatchingMetaDot() {
    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .size(2.dp)
            .clip(CircleShape)
            .background(DduruTheme.colors.gray500)
    )
}

@Composable
private fun MatchingStatusTag(
    text: String,
    iconRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(DduruTheme.colors.gray100)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = DduruTheme.colors.gray500,
            modifier = Modifier.size(10.dp)
        )

        Text(
            text = text,
            style = DduruTheme.typography.Micro2,
            color = DduruTheme.colors.gray500
        )
    }
}

@Composable
private fun MatchingPostThumbnail(
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

        Image(
            painter = painterResource(
                id = if (item.isLiked) {
                    R.drawable.ic_heart_rounded_filled
                } else {
                    R.drawable.ic_heart_rounded
                }
            ),
            contentDescription = null,
            colorFilter = if (item.isLiked) null else ColorFilter.tint(DduruTheme.colors.gray0),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp)
                .size(16.dp)
                .noRippleClickable(onClick = onLikeClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingPostListSectionPreview() {
    DduruTheme {
        MatchingPostListSection(
            items = MatchingUiState.fake.posts,
            onCardClick = {},
            onLikeClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
