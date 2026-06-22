package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.HomeSuperHostCardUiModel

@Composable
fun HomeSuperHostSection(
    items: List<HomeSuperHostCardUiModel>,
    onMoreClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onLikeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "⭐ 슈퍼호스트",
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.black
            )

            HomeSectionTrailingAction(
                text = "전체보기",
                onClick = onMoreClick
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                HomeSuperHostCard(
                    item = item,
                    modifier = Modifier.fillParentMaxWidth(0.82f),
                    onClick = { onCardClick(item.id) },
                    onLikeClick = { onLikeClick(item.id) }
                )
            }
        }
    }
}

@Composable
fun HomeSuperHostCard(
    item: HomeSuperHostCardUiModel,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbnailPainter = item.thumbnailImageRes?.let { painterResource(id = it) }
        ?: ColorPainter(DduruTheme.colors.gray300)
    val hostProfilePainter = item.hostProfileImageRes?.let { painterResource(id = it) }
        ?: ColorPainter(DduruTheme.colors.secondary100)

    Card(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DduruTheme.colors.gray0
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DduruTheme.colors.gray300
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(300f / 280f)
            ) {
                Image(
                    painter = thumbnailPainter,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0f to DduruTheme.colors.black.copy(alpha = 0f),
                                    1f to DduruTheme.colors.black.copy(alpha = 0.5f)
                                )
                            )
                        )
                )

                HomeRecruitingBadge(
                    text = item.recruitmentText,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp, start = 8.dp)
                )

                HomeTripLocationLabel(
                    text = item.location,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 44.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_rounded),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 44.dp)
                        .noRippleClickable(onClick = onLikeClick)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-16.dp))
                    .background(
                        color = DduruTheme.colors.gray0,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = item.title,
                    style = DduruTheme.typography.Body4,
                    color = DduruTheme.colors.gray900
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TripDateRange(
                        date = item.dateRange,
                        textColor = DduruTheme.colors.gray500
                    )

                    Text(
                        text = " · ",
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Caption1,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    HomeGroupMemberCount(
                        current = item.currentMemberCount,
                        max = item.maxMemberCount
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item.hashtags.forEach { hashtag ->
                        HomeHashtag(
                            text = hashtag,
                            textColor = DduruTheme.colors.gray500,
                            textStyle = DduruTheme.typography.Caption1,
                            backgroundColor = DduruTheme.colors.gray100,
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = DduruTheme.colors.gray300)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = hostProfilePainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = item.hostName,
                        modifier = Modifier.weight(1f),
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.black
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_home_eye),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray500,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = item.viewCount.toString(),
                        color = DduruTheme.colors.gray500,
                        style = DduruTheme.typography.Caption2
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeRecruitingBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        color = DduruTheme.colors.secondary100
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_users),
                contentDescription = null,
                tint = DduruTheme.colors.primary
            )

            Text(
                text = text,
                color = DduruTheme.colors.primary,
                style = DduruTheme.typography.Caption1

            )
        }
    }
}

@Composable
private fun HomeTripLocationLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = DduruTheme.colors.gray0
        )

        Text(
            text = text,
            color = DduruTheme.colors.gray0,
            style = DduruTheme.typography.Caption1
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 390)
@Composable
private fun HomeSuperHostSectionPreview() {
    DduruTheme {
        val items = listOf(
            HomeSuperHostCardUiModel(
                id = 1L,
                title = "제주 동쪽 일출 투어",
                location = "제주도 한라산",
                dateRange = "03.15 - 03.18",
                currentMemberCount = 3,
                maxMemberCount = 4,
                hashtags = listOf("일출", "등산"),
                hostName = "username",
                viewCount = 723,
                thumbnailImageRes = R.drawable.fukuoka,
                hostProfileImageRes = R.drawable.img_profile
            ),
            HomeSuperHostCardUiModel(
                id = 2L,
                title = "제주 동쪽 일출 투어",
                location = "제주도 한라산",
                dateRange = "03.15 - 03.18",
                currentMemberCount = 3,
                maxMemberCount = 4,
                hashtags = listOf("일출", "등산"),
                hostName = "username",
                viewCount = 723,
                thumbnailImageRes = R.drawable.fukuoka,
                hostProfileImageRes = R.drawable.img_profile
            )
        )

        Surface(
            color = DduruTheme.colors.gray0
        ) {
            HomeSuperHostSection(
                items = items,
                onMoreClick = {},
                onCardClick = {},
                onLikeClick = {}
            )
        }
    }
}
