package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.model.PreferredDestination

@Composable
fun TravelDestinationPreferenceSection(
    destinations: List<PreferredDestination>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "선호 여행지",
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray800
            )

            if (destinations.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.noRippleClickable(onClick = onAddClick)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_write_pencil),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray500,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "수정",
                        style = DduruTheme.typography.Title3,
                        color = DduruTheme.colors.gray500
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (destinations.isEmpty()) {
            EmptyDestinationCard(onClick = onAddClick)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 17.dp)
            ) {
                destinations.forEachIndexed { index, destination ->
                    DestinationRankRow(rank = index + 1, destination = destination)
                    if (index != destinations.lastIndex) {
                        HorizontalDivider(color = DduruTheme.colors.gray200)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyDestinationCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(127.dp)
            .background(color = DduruTheme.colors.gray100, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = DduruTheme.colors.gray200, shape = RoundedCornerShape(16.dp))
            .noRippleClickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = DduruTheme.colors.gray500, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus_gray),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "선호하는 여행지를 추가해보세요.",
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500
        )
    }
}

@Composable
private fun DestinationRankRow(
    rank: Int,
    destination: PreferredDestination,
    modifier: Modifier = Modifier
) {
    val rankColor = when (rank) {
        1 -> DduruTheme.colors.gray600
        2 -> DduruTheme.colors.gray500
        else -> DduruTheme.colors.gray400
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "${rank}순위",
            style = DduruTheme.typography.Title3,
            color = rankColor,
            modifier = Modifier.width(36.dp)
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = DduruTheme.colors.gray100, shape = CircleShape)
        ) {
            if (destination.imageUrl != null) {
                AsyncImage(
                    model = destination.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                )
            }
        }

        Column {
            Text(
                text = destination.name,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray958
            )
            Text(
                text = destination.region,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray600
            )
        }
    }
}

@Preview(showBackground = true, name = "선호 여행지 - 빈 상태")
@Composable
private fun TravelDestinationPreferenceSectionEmptyPreview() {
    DduruTheme {
        TravelDestinationPreferenceSection(
            destinations = emptyList(),
            onAddClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "선호 여행지 - 선택됨")
@Composable
private fun TravelDestinationPreferenceSectionFilledPreview() {
    DduruTheme {
        TravelDestinationPreferenceSection(
            destinations = listOf(
                PreferredDestination(name = "제주도", region = "국내"),
                PreferredDestination(name = "오사카", region = "일본"),
                PreferredDestination(name = "파리", region = "프랑스")
            ),
            onAddClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
