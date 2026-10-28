package com.gildongmu.dduru.presentation.feature.journey.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class JourneyNewsItemUiState(
    val id: Long,
    val title: String,
    val journeyTitle: String,
    val timeText: String,
    val isUnread: Boolean = false
)

@Composable
fun JourneyNewsSection(
    news: List<JourneyNewsItemUiState>,
    onNewsClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        news.forEachIndexed { index, item ->
            JourneyNewsItem(
                title = item.title,
                journeyTitle = item.journeyTitle,
                timeText = item.timeText,
                isUnread = item.isUnread,
                onClick = { onNewsClick(item.id) }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = DduruTheme.colors.gray200
            )
        }
    }
}

@Composable
fun JourneyNewsItem(
    title: String,
    journeyTitle: String,
    timeText: String,
    isUnread: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray900
                )

                if (isUnread) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = DduruTheme.colors.primary,
                                shape = CircleShape
                            )
                    )
                }
            }

            Text(
                text = journeyTitle,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray700
            )
        }

        Text(
            text = timeText,
            style = DduruTheme.typography.Caption2,
            color = DduruTheme.colors.gray500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyNewsSectionPreview() {
    DduruTheme {
        JourneyNewsSection(
            news = listOf(
                JourneyNewsItemUiState(
                    id = 1,
                    title = "여행 공지가 수정됐어요",
                    journeyTitle = "제주 동쪽 일출 투어",
                    timeText = "5분 전",
                    isUnread = true
                ),
                JourneyNewsItemUiState(
                    id = 2,
                    title = "일정이 변경되었어요",
                    journeyTitle = "제주 서쪽 일출 투어",
                    timeText = "5분 전",
                    isUnread = true
                ),
                JourneyNewsItemUiState(
                    id = 3,
                    title = "투표가 곧 마감됩니다",
                    journeyTitle = "제주 동쪽 일출 투어",
                    timeText = "5분 전",
                    isUnread = false
                )
            ),
            onNewsClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
