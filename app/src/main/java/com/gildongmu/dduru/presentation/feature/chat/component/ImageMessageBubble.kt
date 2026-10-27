package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val SingleImageShape = RoundedCornerShape(16.dp)
private val GridImageShape = RoundedCornerShape(4.dp)
private val GridSpacing = 2.dp

@Composable
fun ImageMessageBubble(
    imageUrls: List<String>,
    time: String,
    unreadCount: Int,
    isMine: Boolean,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (imageUrls.size == 1) {
        Row(
            horizontalArrangement = if (isMine) {
                Arrangement.spacedBy(8.dp, Alignment.End)
            } else {
                Arrangement.spacedBy(8.dp, Alignment.Start)
            },
            verticalAlignment = Alignment.Bottom,
            modifier = modifier.fillMaxWidth()
        ) {
            if (isMine && time.isNotEmpty()) {
                MessageTimeInfo(time = time, unreadCount = unreadCount, isMine = true)
            }
            AsyncImage(
                model = imageUrls.first(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 220.dp, height = 180.dp)
                    .clip(SingleImageShape)
                    .background(DduruTheme.colors.gray200, SingleImageShape)
                    .noRippleClickable { onImageClick(0) }
            )
            if (!isMine && time.isNotEmpty()) {
                MessageTimeInfo(time = time, unreadCount = unreadCount, isMine = false)
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start,
            modifier = modifier.fillMaxWidth()
        ) {
            ImageGrid(
                imageUrls = imageUrls,
                onImageClick = onImageClick
            )
            if (time.isNotEmpty()) {
                MessageTimeInfo(time = time, unreadCount = unreadCount, isMine = isMine)
            }
        }
    }
}

@Composable
fun GroupImageMessageBubble(
    username: String,
    imageUrls: List<String>,
    time: String,
    unreadCount: Int,
    isHead: Boolean,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isHead) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DduruTheme.colors.gray100)
            )
        } else {
            Box(modifier = Modifier.size(32.dp))
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            if (isHead) {
                Text(
                    text = username,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray846
                )
            }
            ImageMessageBubble(
                imageUrls = imageUrls,
                time = time,
                unreadCount = unreadCount,
                isMine = false,
                onImageClick = onImageClick
            )
        }
    }
}

@Composable
private fun ImageGrid(
    imageUrls: List<String>,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val rowSizes = imageGridRowSizes(imageUrls.size)
    val rowStarts = rowSizes.runningFold(0) { acc, count -> acc + count }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val cellHeight = (maxWidth - GridSpacing * 2) / 3

        Column(verticalArrangement = Arrangement.spacedBy(GridSpacing)) {
            rowSizes.forEachIndexed { rowIndex, count ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(GridSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cellHeight)
                ) {
                    repeat(count) { offset ->
                        val index = rowStarts[rowIndex] + offset
                        AsyncImage(
                            model = imageUrls[index],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(GridImageShape)
                                .background(DduruTheme.colors.gray200, GridImageShape)
                                .noRippleClickable { onImageClick(index) }
                        )
                    }
                }
            }
        }
    }
}

// 3장씩 채우고 남는 사진은 2장씩 배치 (예: 10장 = 3, 3, 2, 2)
private fun imageGridRowSizes(count: Int): List<Int> {
    if (count <= 1) return listOf(count)
    val fullRows = count / 3
    return when (count % 3) {
        0 -> List(fullRows) { 3 }
        1 -> List(fullRows - 1) { 3 } + listOf(2, 2)
        else -> List(fullRows) { 3 } + 2
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageMessageBubbleSinglePreview() {
    DduruTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            GroupImageMessageBubble(
                username = "username",
                imageUrls = listOf(""),
                time = "15:23",
                unreadCount = 1,
                isHead = true,
                onImageClick = {}
            )
            ImageMessageBubble(
                imageUrls = listOf(""),
                time = "15:23",
                unreadCount = 1,
                isMine = true,
                onImageClick = {}
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 600)
@Composable
private fun ImageMessageBubbleGridPreview() {
    DduruTheme {
        GroupImageMessageBubble(
            username = "username",
            imageUrls = List(10) { "" },
            time = "15:23",
            unreadCount = 1,
            isHead = true,
            onImageClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
