package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private val PastJourneyCardShape = RoundedCornerShape(16.dp)
private val PastJourneyImageShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

@Composable
fun PastJourneyCard(
    id: Int,
    title: String,
    location: String,
    date: String,
    imageUrl: String?,
    memberImageUrls: List<String>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(200.dp)
            .clip(PastJourneyCardShape)
            .noRippleClickable { onClick(id) }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(200f / 130f)
                .clip(PastJourneyImageShape)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-15).dp)
                .clip(PastJourneyCardShape)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = DduruTheme.colors.black.copy(alpha = 0.12f),
                    spotColor = DduruTheme.colors.black.copy(alpha = 0.12f)
                )
                .background(DduruTheme.colors.gray0)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = title,
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_location),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray500,
                        modifier = Modifier.size(8.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = location,
                        style = DduruTheme.typography.Micro3,
                        color = DduruTheme.colors.gray500,
                        maxLines = 1
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(2.dp)
                            .background(DduruTheme.colors.gray500, CircleShape)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_ai_calendar),
                        contentDescription = null,
                        tint = DduruTheme.colors.gray500,
                        modifier = Modifier.size(8.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = date,
                        style = DduruTheme.typography.Micro3,
                        color = DduruTheme.colors.gray500,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    memberImageUrls.forEachIndexed { index, memberImageUrl ->
                        AsyncImage(
                            model = memberImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .offset(x = (-6 * index).dp)
                                .size(13.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = DduruTheme.colors.gray0,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun PastJourneyCardPreview() {
    DduruTheme {
        PastJourneyCard(
            id = 1,
            title = "후쿠오카 미식 여행",
            location = "후쿠오카 일본",
            date = "08.27 - 08.30",
            imageUrl = "https://images.unsplash.com/photo-1528164344705-47542687000d",
            memberImageUrls = listOf(
                "https://i.pravatar.cc/96?img=12",
                "https://i.pravatar.cc/96?img=32",
                "https://i.pravatar.cc/96?img=47",
                "https://i.pravatar.cc/96?img=60"
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
