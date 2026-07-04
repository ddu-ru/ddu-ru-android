package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PollVoteItem(
    label: String,
    isSelected: Boolean,
    voteCount: Int?,
    totalVotes: Int = 0,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    val progressShape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp)
    val showResult = voteCount != null

    val borderColor = when {
        showResult && isSelected -> DduruTheme.colors.secondary
        showResult && !isSelected -> DduruTheme.colors.gray200
        isSelected -> DduruTheme.colors.primary
        else -> DduruTheme.colors.gray200
    }
    val bgColor = when {
        showResult && isSelected -> DduruTheme.colors.secondary100
        isSelected -> DduruTheme.colors.secondary
        else -> DduruTheme.colors.gray0
    }
    val progressColor = if (isSelected) DduruTheme.colors.secondary else DduruTheme.colors.gray200
    val labelColor = when {
        isSelected -> DduruTheme.colors.primary
        showResult -> DduruTheme.colors.gray500
        else -> DduruTheme.colors.gray900
    }
    val countColor = if (isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray500
    val ratio = if (showResult && totalVotes > 0) {
        (voteCount!!.toFloat() / totalVotes).coerceIn(0f, 1f)
    } else {
        0f
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, borderColor, shape)
            .background(bgColor, shape)
            .clip(shape)
            .noRippleClickable(onClick = onClick)
    ) {
        if (showResult) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = ratio)
                    .background(progressColor, progressShape)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = DduruTheme.typography.Body1,
                    color = labelColor
                )
                if (isSelected) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = DduruTheme.colors.primary
                    )
                }
            }
            if (showResult) {
                Text(
                    text = "${voteCount}표",
                    style = DduruTheme.typography.Body1,
                    color = countColor
                )
            }
        }
    }
}

@Composable
fun PollVoteList(
    title: String,
    items: List<String>,
    selectedIndex: Int?,
    voteCounts: List<Int>?,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalVotes = voteCounts?.sum() ?: 0
    val borderColor = DduruTheme.colors.gray200

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val stroke = 2.dp.toPx()
                    val half = stroke / 2f
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height - half),
                        end = Offset(size.width, size.height - half),
                        strokeWidth = stroke
                    )
                }
                .height(48.dp)
                .padding(start = 16.dp, top = 3.dp, end = 16.dp, bottom = 3.dp)
        ) {
            Text(
                text = title,
                style = DduruTheme.typography.Body1,
                color = DduruTheme.colors.gray958
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, label ->
                PollVoteItem(
                    label = label,
                    isSelected = selectedIndex == index,
                    voteCount = voteCounts?.get(index),
                    totalVotes = totalVotes,
                    onClick = { onItemClick(index) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PollVoteListDefaultPreview() {
    DduruTheme {
        PollVoteList(
            title = "어쩌구 투표",
            items = listOf("Subject1", "Subject2", "Subject3"),
            selectedIndex = null,
            voteCounts = null,
            onItemClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollVoteListSelectedPreview() {
    DduruTheme {
        PollVoteList(
            title = "어쩌구 투표",
            items = listOf("Subject1", "Subject2", "Subject3"),
            selectedIndex = 0,
            voteCounts = null,
            onItemClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PollVoteListResultPreview() {
    DduruTheme {
        PollVoteList(
            title = "어쩌구 투표",
            items = listOf("Subject1", "Subject2", "Subject3"),
            selectedIndex = 0,
            voteCounts = listOf(5, 3, 1),
            onItemClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
