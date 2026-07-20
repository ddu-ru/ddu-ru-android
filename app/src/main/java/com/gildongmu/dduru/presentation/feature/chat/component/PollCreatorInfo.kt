package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun PollCreatorInfo(
    avatarUrl: String?,
    username: String,
    createdAt: String,
    readCount: Int,
    remainingTime: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(DduruTheme.colors.gray200, CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = username,
                style = DduruTheme.typography.Caption2,
                color = DduruTheme.colors.gray800
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = createdAt,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500
                )
                Dot()
                Text(
                    text = "${readCount}명 읽음",
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500
                )
                Dot()
                Text(
                    text = remainingTime,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray500
                )
            }
        }
    }
}

@Composable
private fun Dot() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .background(DduruTheme.colors.gray500, CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun PollCreatorInfoPreview() {
    DduruTheme {
        PollCreatorInfo(
            avatarUrl = null,
            username = "username",
            createdAt = "3월 26일 13:09",
            readCount = 6,
            remainingTime = "투표 종료 5시간 전",
            modifier = Modifier.padding(16.dp)
        )
    }
}
