package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.HomePopularDestinationUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRealtimePopularDestinationSection(
    items: ImmutableList<HomePopularDestinationUiModel>,
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
                Text(text = "✈\uFE0F") // DS 적용

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "실시간 인기", // DS 적용
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            RefreshButton(
                updatedText = updatedText,
                onClick = onRefreshClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = items, key = { it.id }) { item ->
                HomeBasicCard(
                    rank = item.rank,
                    destination = item.destinationName,
                    travelerCount = item.waitingMateCount,
                    hashtags = item.hashtags,
                    companionCount = item.waitingMateCount,
                    imageUrl = item.imageUrl,
                    onClick = { onItemClick(item) },
                    onJoinClick = { onItemClick(item) },
                    modifier = Modifier
                        .width(267.dp)
                        .aspectRatio(267f / 166f)
                )
            }
        }
    }
}

@Composable
private fun HomeBasicCard(
    imageUrl: String,
    rank: Int,
    destination: String,
    travelerCount: Int,
    hashtags: ImmutableList<String>,
    companionCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onJoinClick: () -> Unit = {},
) {

    Box(
        modifier = modifier
                .clip(RoundedCornerShape(24.dp))
                .noRippleClickable(onClick = onClick)
    ) {

        AsyncImage(
            model = imageUrl.takeIf { it.isNotBlank() },
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .background(DduruTheme.colors.secondary100),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0f),
                        ),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp),
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = rank.toString(),
                        color = DduruTheme.colors.gray0,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold, // DS 적용예정
                    )


                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text ="|",
                        color = DduruTheme.colors.gray0,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold, // DS 적용예정
                    )

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(38.dp) // 이거 바꾸기 (디자인관련 무늬 나무니,,)
                            .background(DduruTheme.colors.gray0),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = destination,
                            color = DduruTheme.colors.gray0,
                            style = DduruTheme.typography.Body1
                        )

                        Text(
                            text = "${travelerCount}명이 찾는 여행지",
                            color = DduruTheme.colors.gray400,
                            style = DduruTheme.typography.Micro2
                        )

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                HomeHashtags(
                    hashtags = hashtags,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            HomeCompanionJoinBar(
                companionCount = companionCount,
                onJoin = onJoinClick,
            )
        }

    }
}

@Composable
private fun HomeHashtags(
    hashtags: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        hashtags.take(3).forEach { hashtag ->
            HomeHashtag(
                text = hashtag.removePrefix("#"),
                textColor = DduruTheme.colors.gray0,
                textStyle = DduruTheme.typography.Micro2, // DS 다시 적용
                backgroundColor = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
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
                    text = "🔥",
                    style = DduruTheme.typography.Micro2
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
private fun RefreshButton(
    updatedText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home_arrowscounterclockwise),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = updatedText,
            style = DduruTheme.typography.Caption1,
            color = DduruTheme.colors.gray600
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

@Preview
@Composable
private fun HomeBasicCardPreview() {
    DduruTheme {
        HomeBasicCard(
            imageUrl = "",
            rank = 1,
            destination = "제주도",
            travelerCount = 14231,
            hashtags = persistentListOf("힐링", "드라이브", "바다"),
            companionCount = 8,
            modifier = Modifier
                .width(267.dp)
                .aspectRatio(267f / 166f)
        )
    }

}
@Preview(showBackground = true, widthDp = 375)
@Composable
private fun HomeRealtimePopularDestinationSectionPreview() {
    DduruTheme {
        HomeRealtimePopularDestinationSection(
            items = persistentListOf(
                HomePopularDestinationUiModel(
                    id = 1L,
                    rank = 1,
                    destinationName = "제주도",
                    waitingMateCount = 14231,
                    whyHotText = "유채꽃이 활짝 폈어요!",
                    hashtags = persistentListOf("힐링", "드라이브", "바다"),
                    imageUrl = ""
                ), HomePopularDestinationUiModel(
                    id = 2L,
                    rank = 2,
                    destinationName = "강릉",
                    waitingMateCount = 8231,
                    whyHotText = "바다 보러 떠나기 딱 좋아요!",
                    hashtags = persistentListOf("오션뷰", "카페", "산책"),
                    imageUrl = ""
                )
            ), updatedText = "방금 전", onRefreshClick = {}, onItemClick = {})
    }
}
