package com.gildongmu.dduru.presentation.feature.journey

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val JourneyCardShape = RoundedCornerShape(32.dp)

private const val JOURNEY_CARD_RATIO = 343f / 351f
private const val JOURNEY_IMAGE_RATIO = 343f / 240f

@Composable
fun JourneyCard(
    imageUrl: String?,
    dDay: String,
    title: String,
    period: String,
    memberCount: Int,
    remainingTodoCount: Int,
    newNewsCount: Int,
    onJourneyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(JOURNEY_CARD_RATIO),
    ) {
        JourneyImageContent(
            imageUrl = imageUrl,
            dDay = dDay,
            title = title,
            period = period,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(JOURNEY_IMAGE_RATIO)
                .align(Alignment.TopCenter),
        )

        JourneyInformationContent(
            memberCount = memberCount,
            remainingTodoCount = remainingTodoCount,
            newNewsCount = newNewsCount,
            onJourneyClick = onJourneyClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun JourneyImageContent(
    imageUrl: String?,
    dDay: String,
    title: String,
    period: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(JourneyCardShape),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.12f),
                            Color.Black.copy(alpha = 0.72f),
                        ),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 16.dp,
                    bottom = 49.dp
                ),
        ) {
            Text(
                text = dDay,
                style = DduruTheme.typography.H1,
                color = Color.White,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = DduruTheme.typography.H1,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = period,
                style = DduruTheme.typography.Body1,
                color = Color.White,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun JourneyInformationContent(
    memberCount: Int,
    remainingTodoCount: Int,
    newNewsCount: Int,
    onJourneyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = JourneyCardShape,
                clip = false,
            )
            .clip(JourneyCardShape)
            .background(DduruTheme.colors.gray0)
            .padding(
                start = 16.dp,
                bottom = 16.dp,
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JourneySummaryItem(
                iconRes = R.drawable.ic_journey_users,
                label = "멤버",
                value = "${memberCount}명",
                iconBackgroundColor = Color(0xFFE7F4FF),
                iconTint = Color(0xFF2F9DF4),
                modifier = Modifier.weight(1f),
            )

            JourneySummaryItem(
                iconRes = R.drawable.ic_write_pencil,
                label = "남은 할일",
                value = "${remainingTodoCount}개",
                iconBackgroundColor = Color(0xFFE8F8F8),
                iconTint = Color(0xFF2DB5BB),
                modifier = Modifier.weight(1f),
            )

            JourneySummaryItem(
                iconRes = R.drawable.ic_news,
                label = "새 소식",
                value = "${newNewsCount}개",
                iconBackgroundColor = Color(0xFFFFEEEE),
                iconTint = Color(0xFFF35E68),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DduruButton(
            text = "여정 들어가기",
            onClick = onJourneyClick,
            enabled = true,
            enabledColors = DduruButtonDefaults.filledEnabledColors,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        )
    }
}

@Composable
private fun JourneySummaryItem(
    @DrawableRes iconRes: Int,
    label: String,
    value: String,
    iconBackgroundColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = iconBackgroundColor,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = label,
                style = DduruTheme.typography.Caption1,
                color = DduruTheme.colors.gray500,
                maxLines = 1,
            )

            Text(
                text = value,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.black,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun JourneyCardPreview() {
    DduruTheme {
        JourneyCard(
            imageUrl = "https://images.unsplash.com/photo-1528164344705-47542687000d",
            dDay = "D-23",
            title = "후쿠오카 미식 여행",
            period = "2026. 08. 27 - 2026. 08. 30",
            memberCount = 4,
            remainingTodoCount = 6,
            newNewsCount = 2,
            onJourneyClick = { },
            modifier = Modifier.padding(16.dp),
        )
    }
}
