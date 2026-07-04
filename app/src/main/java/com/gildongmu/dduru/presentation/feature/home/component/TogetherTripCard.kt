package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.home.model.TravelCompanionUiModel

@Composable
fun TravelCompanionSection(
    items: List<TravelCompanionUiModel>,
    title: String,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {},
    onCardClick: (TravelCompanionUiModel) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏝️"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = DduruTheme.typography.Title,
                color = DduruTheme.colors.gray900
            )

            Spacer(modifier = Modifier.weight(1f))

            HomeSectionTrailingAction(
                text = "전체보기",
                onClick = onMoreClick
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                TravelCompanionCard(
                    item = item,
                    onClick = { onCardClick(item) }
                )
            }
        }
    }
}

@Composable
private fun TravelCompanionCard(
    item: TravelCompanionUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DduruTheme.colors.gray0
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(DduruTheme.colors.gray200)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            bottomStart = 24.dp
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .weight(2.4f)
                    .padding(horizontal = 20.dp, vertical = 13.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 3.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = DduruTheme.typography.Body1, // 여기 DS가 안나옴 !!
                        color = DduruTheme.colors.gray900
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

                HomeLocationCount(
                    location = item.location,
                    color = DduruTheme.colors.gray500,
                    textStyle = DduruTheme.typography.Caption2
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TripDateRange(
                        date = item.dateText,
                        textColor = DduruTheme.colors.gray500,
                        textStyle = DduruTheme.typography.Caption2
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "·",
                        style = DduruTheme.typography.Caption2,
                        color = DduruTheme.colors.gray500
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    HomeMemberCount(
                        current = item.currentMemberCount,
                        max = item.totalMemberCount,
                        textColor = DduruTheme.colors.gray500,
                        textStyle = DduruTheme.typography.Caption2
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F6F8
)
@Composable
private fun TravelCompanionSectionPreview() {
    DduruTheme {
        val items = listOf(
            TravelCompanionUiModel(
                id = 1L,
                title = "봄맞이 한라산 여행",
                location = "제주도 한라산",
                dateText = "03.15-03.18",
                currentMemberCount = 3,
                totalMemberCount = 4,
                imageRes = R.drawable.img_home_empty_penguin
            ),
            TravelCompanionUiModel(
                id = 2L,
                title = "같이 가실 분 구해요",
                location = "제주도 한라산",
                dateText = "03.15-03.18",
                currentMemberCount = 3,
                totalMemberCount = 4,
                imageRes = R.drawable.img_home_empty_penguin
            ),
            TravelCompanionUiModel(
                id = 3L,
                title = "혼자 말고 같이 등산해요",
                location = "제주도 한라산",
                dateText = "03.15-03.18",
                currentMemberCount = 3,
                totalMemberCount = 4,
                imageRes = R.drawable.img_home_empty_penguin
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DduruTheme.colors.gray100)
                .padding(20.dp)
        ) {
            TravelCompanionSection(items = items, title = "내가 가고 싶은 동행지")
        }
    }
}
