package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyDetailHeader(
    imageUrl: String?,
    dDay: String,
    title: String,
    period: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color.Black.copy(alpha = 0.5f)
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 19.dp,
                    end = 16.dp,
                    bottom = 49.dp
                ),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "다음 여행까지",
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray0
            )

            Text(
                text = dDay,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    lineHeight = 38.4.sp,
                    letterSpacing = 0.sp
                ),
                color = DduruTheme.colors.gray0
            )

            Text(
                text = title,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray0,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = period,
                style = DduruTheme.typography.Title3,
                color = DduruTheme.colors.gray0
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyDetailHeaderPreview() {
    DduruTheme {
        JourneyDetailHeader(
            imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
            dDay = "D-14",
            title = "제주도 힐링 여행",
            period = "2026.09.08-09.11",
            modifier = Modifier.height(240.dp)
        )
    }
}
