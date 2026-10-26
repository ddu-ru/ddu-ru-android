package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class ContentTab { PHOTO, POLL }

data class PollSummary(
    val title: String,
    val participantCount: Int,
    val isOngoing: Boolean,
    val endDateTime: String
)

@Composable
fun ContentCollectionCard(
    selectedTab: ContentTab,
    onTabSelected: (ContentTab) -> Unit,
    photoUrls: List<String>,
    polls: List<PollSummary>,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        modifier = modifier
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .background(DduruTheme.colors.gray0, shape)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            Text(
                text = "콘텐츠 모아보기",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray800
            )
            ContentTabRow(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }

        when (selectedTab) {
            ContentTab.PHOTO -> ContentPhotoGrid(photoUrls = photoUrls)
            ContentTab.POLL -> ContentPollList(polls = polls)
        }

        DduruButton(
            text = "모두보기",
            onClick = onViewAllClick,
            enabledColors = DduruButtonEnabledColors(
                backgroundColor = DduruTheme.colors.gray100,
                textColor = DduruTheme.colors.gray700,
                borderColor = DduruTheme.colors.gray300
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ContentTabRow(
    selectedTab: ContentTab,
    onTabSelected: (ContentTab) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContentTabChip(
            label = "사진",
            selected = selectedTab == ContentTab.PHOTO,
            onClick = { onTabSelected(ContentTab.PHOTO) }
        )
        ContentTabChip(
            label = "투표",
            selected = selectedTab == ContentTab.POLL,
            onClick = { onTabSelected(ContentTab.POLL) }
        )
    }
}

@Composable
private fun ContentTabChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (selected) DduruTheme.colors.black else DduruTheme.colors.gray50
    val textColor = if (selected) DduruTheme.colors.gray0 else DduruTheme.colors.gray600
    val borderColor = if (selected) DduruTheme.colors.black else DduruTheme.colors.gray100

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(40.dp)
            .border(1.dp, borderColor, RoundedCornerShape(99.dp))
            .background(bgColor, RoundedCornerShape(99.dp))
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body4,
            color = textColor
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContentPhotoGrid(photoUrls: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        photoUrls.take(6).forEach { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(98.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DduruTheme.colors.gray200, RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
private fun ContentPollList(polls: List<PollSummary>) {
    Column {
        polls.forEach { poll ->
            ContentPollItem(poll = poll)
        }
    }
}

@Composable
private fun ContentPollItem(poll: PollSummary) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Q",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray900
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = poll.title,
                    style = DduruTheme.typography.Body1,
                    color = DduruTheme.colors.gray900
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${poll.participantCount}명 참여",
                            style = DduruTheme.typography.Body4,
                            color = DduruTheme.colors.gray500
                        )
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(DduruTheme.colors.gray500, CircleShape)
                        )
                        Text(
                            text = if (poll.isOngoing) "진행중" else "종료",
                            style = DduruTheme.typography.Body4,
                            color = DduruTheme.colors.gray500
                        )
                    }
                    Text(
                        text = poll.endDateTime,
                        style = DduruTheme.typography.Body4,
                        color = DduruTheme.colors.gray500
                    )
                }
            }
        }
        HorizontalDivider(
            color = DduruTheme.colors.gray200,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCollectionCardPhotoPreview() {
    DduruTheme {
        ContentCollectionCard(
            selectedTab = ContentTab.PHOTO,
            onTabSelected = {},
            photoUrls = List(6) { "" },
            polls = emptyList(),
            onViewAllClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCollectionCardPollPreview() {
    DduruTheme {
        ContentCollectionCard(
            selectedTab = ContentTab.POLL,
            onTabSelected = {},
            photoUrls = emptyList(),
            polls = listOf(
                PollSummary("제목", 12, true, "2026년 3월 10일 17:00 종료"),
                PollSummary("제목", 12, true, "2026년 3월 10일 17:00 종료")
            ),
            onViewAllClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
