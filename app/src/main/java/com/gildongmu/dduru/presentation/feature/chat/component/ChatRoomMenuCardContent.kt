package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ChatRoomMenuCardContent(
    imageUrl: String?,
    title: String,
    participantCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(modifier = Modifier.size(64.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DduruTheme.colors.gray200, RoundedCornerShape(16.dp))
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .border(
                        width = 4.dp,
                        color = DduruTheme.colors.gray50,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(32.dp)
                    .background(DduruTheme.colors.secondary100, RoundedCornerShape(16.dp))
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_home_users),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = DduruTheme.colors.primary
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = DduruTheme.typography.Title1,
                color = DduruTheme.colors.gray800
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(DduruTheme.colors.gray200, RoundedCornerShape(99.dp))
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_home_users),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = DduruTheme.colors.gray600
                )
                Text(
                    text = "${participantCount}명 참여중",
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatRoomMenuCardContentPreview() {
    DduruTheme {
        ChatRoomMenuCardContent(
            imageUrl = null,
            title = "제주 애월 2박 3일",
            participantCount = 3,
            modifier = Modifier.padding(16.dp)
        )
    }
}
