package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.HomePopularDestinationUiModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeRealtimePopularDestinationSection(
    items: List<HomePopularDestinationUiModel>,
    updatedText: String,
    onRefreshClick: () -> Unit,
    onItemClick: (HomePopularDestinationUiModel) -> Unit,
    modifier: Modifier = Modifier
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
                    text = "🔥", //유니코드로 대체
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "실시간 인기 여행지",
                    style = DduruTheme.typography.Title1,
                    color = DduruTheme.colors.black
                )
            }

            Row(
                modifier = Modifier.clickable(onClick = onRefreshClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "↻",
                    fontSize = 18.sp,
                    color = DduruTheme.colors.gray400
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = updatedText,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray400
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                HomeRealtimePopularDestinationCard(
                    item = item,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.fillParentMaxWidth(0.82f)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeRealtimePopularDestinationCard(
    item: HomePopularDestinationUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DduruTheme.colors.gray0)
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray200,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DestinationThumbnail(
                imageUrl = item.imageUrl
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#${item.rank}",
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.primary,
                        maxLines = 1,
                        softWrap = false
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = item.destinationName,
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${item.waitingMateCount}명의 메이트 대기중",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        WhyHotBox(
            text = item.whyHotText
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item.hashtags.forEach { hashtag ->
                HomeHashtag(
                    text = hashtag,
                    textColor = DduruTheme.colors.gray700,
                    textStyle = DduruTheme.typography.Micro2,
                    backgroundColor = DduruTheme.colors.gray100,
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun WhyHotBox(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DduruTheme.colors.secondary100)
            .padding(all = 8.dp)
    ) {
        Text(
            text = "✦ WHY HOT?",
            style = DduruTheme.typography.Micro,
            color = DduruTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "\"$text\"",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray846
        )
    }
}

@Composable
private fun DestinationThumbnail(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // TODO:
    // 서버 이미지 붙일 때는 Coil의 AsyncImage로 교체
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(DduruTheme.colors.gray50),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🏝️",
            fontSize = 28.sp
        )
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
                ),
                HomePopularDestinationUiModel(
                    id = 2L,
                    rank = 2,
                    destinationName = "강릉",
                    waitingMateCount = 8231,
                    whyHotText = "바다 보러 떠나기 딱 좋아요!",
                    hashtags = listOf("오션뷰", "카페", "산책"),
                    imageUrl = ""
                )
            ),
            updatedText = "방금 전",
            onRefreshClick = {},
            onItemClick = {}
        )
    }
}
