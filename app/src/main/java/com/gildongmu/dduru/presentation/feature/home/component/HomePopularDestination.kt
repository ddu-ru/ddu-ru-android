package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.HomePopularDestinationUiModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeRealtimePopularDestinationSection(
    items: List<HomePopularDestinationUiModel>,
    updatedText: String,
    onRefreshClick: () -> Unit,
    onItemClick: (HomePopularDestinationUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✈\uFE0F"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "실시간 인기", // semi ,+ 8 font
                    style = DduruTheme.typography.Title1, color = DduruTheme.colors.black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            RefreshButton(
                updatedText = updatedText, onClick = onRefreshClick, modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items, key = { it.id }) { item ->
                HomeRealtimePopularDestinationCard(
                    rank = item.rank,
                    destinationName = item.destinationName,
                    waitingMateCount = item.waitingMateCount,
                    whyHotText = item.whyHotText,
                    hashtags = item.hashtags,
                    imageUrl = item.imageUrl,
                    onClick = { onItemClick(item) },
                    modifier = Modifier
                        .fillParentMaxWidth(0.52f)
                        .widthIn(max = 200.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeBasicCard(
    rank: Int,
    location: String,
    waitingMateCount: Int,
    hashtags: ImmutableList<String>,
    companionCount: Int,

    ) {

}

@Composable
private fun HomeCompanionJoinBar(
    companionCount: Int,
    onJoin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, shape = RoundedCornerShape(12.dp), color = DduruTheme.colors.gray0.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(DduruTheme.colors.gray0.copy(alpha = 0.8f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🔥", style = DduruTheme.typography.Micro2
                )

                Text(
                    text = "참여 가능한 동행 ${companionCount}개",
                    color = DduruTheme.colors.black,
                    style = DduruTheme.typography.Micro2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.noRippleClickable(onJoin),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "바로 참여하기", color = DduruTheme.colors.primary, style = DduruTheme.typography.Micro2
                )

                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    modifier = Modifier.size(8.dp),
                    tint = DduruTheme.colors.primary
                )

            }
        }

    }
}

@Composable
fun HomeRealtimePopularDestinationCard(
    rank: Int,
    destinationName: String,
    waitingMateCount: Int,
    whyHotText: String,
    hashtags: List<String>,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DestinationThumbnail(
                imageUrl = imageUrl
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#$rank",
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.primary,
                        maxLines = 1,
                        softWrap = false
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = destinationName,
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${waitingMateCount}명의 메이트 대기중",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        WhyHotBox(
            text = whyHotText
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            hashtags.forEach { hashtag ->
                HomeHashtag(
                    text = hashtag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Micro2,
                    backgroundColor = DduruTheme.colors.gray100,
                    contentPadding = PaddingValues(
                        horizontal = 8.dp, vertical = 4.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun WhyHotBox(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DduruTheme.colors.secondary100)
            .padding(all = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home_twinkle), contentDescription = null
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "WHY HOT?", style = DduruTheme.typography.Micro, color = DduruTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "\"$text\"", style = DduruTheme.typography.Caption2, color = DduruTheme.colors.gray846
        )
    }
}

@Composable
private fun DestinationThumbnail(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    val thumbnailModifier = modifier
        .size(32.dp)
        .clip(CircleShape)

    if (imageUrl.isNotBlank()) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = thumbnailModifier,
            loading = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(DduruTheme.colors.secondary100)
                )
            },
            error = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(DduruTheme.colors.secondary100)
                )
            })
    } else {
        Box(
            modifier = thumbnailModifier.background(DduruTheme.colors.secondary100)
        )
    }
}

@Composable
private fun RefreshButton(
    updatedText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home_arrowscounterclockwise), contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = updatedText, style = DduruTheme.typography.Caption1, color = DduruTheme.colors.gray600
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF5F)
@Composable
private fun HomeCompanionJoinBarPreview() {
    DduruTheme {
        HomeCompanionJoinBar(
            companionCount = 24, onJoin = {})
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun HomeRealtimePopularDestinationSectionPreview() {
    DduruTheme {
        HomeRealtimePopularDestinationSection(
            items = listOf(
            HomePopularDestinationUiModel(
                id = 1L,
                rank = 1,
                destinationName = "제주도",
                waitingMateCount = 14231,
                whyHotText = "유채꽃이 활짝 폈어요!",
                hashtags = listOf("힐링", "드라이브", "바다"),
                imageUrl = ""
            ), HomePopularDestinationUiModel(
                id = 2L,
                rank = 2,
                destinationName = "강릉",
                waitingMateCount = 8231,
                whyHotText = "바다 보러 떠나기 딱 좋아요!",
                hashtags = listOf("오션뷰", "카페", "산책"),
                imageUrl = ""
            )
        ), updatedText = "방금 전", onRefreshClick = {}, onItemClick = {})
    }
}
