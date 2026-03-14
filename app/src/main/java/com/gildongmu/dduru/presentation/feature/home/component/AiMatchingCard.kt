package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonDefaults
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class AiMatchingCardData(
    val imageUrl: String? = null,
    val matchPercent: Int,
    val title: String,
    val location: String,
    val dateRange: String,
    val profileImageUrl: String? = null,
    val username: String,
    val age: Int,
    val gender: String,
    val currentMembers: Int,
    val maxMembers: Int,
    val description: String,
    val hashtags: List<String>
)

@Composable
fun AiMatchingCard(
    data: AiMatchingCardData,
    onPass: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier
) {
    var overlayType by remember { mutableStateOf<OverlayType?>(null) }
    val dividerColor = DduruTheme.colors.gray0.copy(alpha = 0.3f)
    val badgeBorderColor = DduruTheme.colors.gray0.copy(alpha = 0.05f)
    val badgeBackgroundColor = DduruTheme.colors.black.copy(alpha = 0.1f)
    val overlayBackgroundColor = DduruTheme.colors.black.copy(alpha = 0.6f)
    val overlayCircleColor = if (overlayType == OverlayType.ACCEPT) {
        DduruTheme.colors.secondary
    } else {
        DduruTheme.colors.grayNeutral.copy(alpha = 0.3f)
    }
    val overlayIconRes = if (overlayType == OverlayType.ACCEPT) {
        R.drawable.ic_heart_rounded
    } else {
        R.drawable.ic_pass
    }

    Box(
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    ) {
        SubcomposeAsyncImage(
            model = data.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            loading = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(DduruTheme.colors.gray700)
                )
            },
            error = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(DduruTheme.colors.gray700)
                )
            }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(DduruTheme.colors.black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = data.title,
                    style = DduruTheme.typography.H1,
                    color = DduruTheme.colors.gray0
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HomeLocationCount(
                        location = data.location,
                        color = DduruTheme.colors.gray200,
                        textStyle = DduruTheme.typography.Caption2
                    )

                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(DduruTheme.colors.gray200, CircleShape)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ai_calendar),
                            contentDescription = null,
                            tint = DduruTheme.colors.gray200,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = data.dateRange,
                            style = DduruTheme.typography.Caption2,
                            color = DduruTheme.colors.gray200
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = dividerColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (data.profileImageUrl != null) {
                        SubcomposeAsyncImage(
                            model = data.profileImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(DduruTheme.colors.gray400)
                                )
                            },
                            error = {
                                Image(
                                    painter = painterResource(id = R.drawable.img_profile),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.img_profile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = data.username,
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.gray0
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${data.age}세",
                                style = DduruTheme.typography.Caption2,
                                color = DduruTheme.colors.gray400
                            )

                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .background(DduruTheme.colors.gray400, CircleShape)
                            )

                            Text(
                                text = data.gender,
                                style = DduruTheme.typography.Caption2,
                                color = DduruTheme.colors.gray400
                            )
                        }
                    }
                }

                HomeMemberCount(
                    current = data.currentMembers,
                    max = data.maxMembers,
                    textColor = DduruTheme.colors.gray400,
                    textStyle = DduruTheme.typography.Caption2
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                Text(
                    text = data.description,
                    style = DduruTheme.typography.Caption2,
                    color = DduruTheme.colors.gray0,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    data.hashtags.forEach { tag ->
                        HomeHashtag(
                            text = tag,
                            textColor = DduruTheme.colors.gray300,
                            textStyle = DduruTheme.typography.Caption2,
                            backgroundColor = DduruTheme.colors.grayBorder.copy(alpha = 0.3f),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DduruButton(
                    text = "패스",
                    onClick = {
                        overlayType = OverlayType.PASS
                        onPass()
                    },
                    enabledColors = DduruButtonDefaults.outlinedEnabledColors,
                    disabledColors = DduruButtonDefaults.outlinedDisabledColors,
                    textStyle = DduruTheme.typography.Title1,
                    iconRes = R.drawable.ic_pass,
                    modifier = Modifier.weight(1f)
                )

                DduruButton(
                    text = "함께할래요",
                    onClick = {
                        overlayType = OverlayType.ACCEPT
                        onAccept()
                    },
                    textStyle = DduruTheme.typography.Title1,
                    iconRes = R.drawable.ic_heart_rounded,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .background(
                    color = badgeBackgroundColor,
                    shape = RoundedCornerShape(99.dp)
                )
                .border(
                    width = 1.dp,
                    color = badgeBorderColor,
                    shape = RoundedCornerShape(99.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ai_star),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray0,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${data.matchPercent}% 일치",
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray0
                )
            }
        }

        AnimatedVisibility(
            visible = overlayType != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        color = overlayBackgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .shadow(
                            elevation = 32.dp,
                            shape = CircleShape,
                            spotColor = DduruTheme.colors.black.copy(alpha = 0.9f),
                            ambientColor = DduruTheme.colors.black.copy(alpha = 0.9f)
                        )
                        .background(
                            color = overlayCircleColor,
                            shape = CircleShape
                        )
                        .padding(
                            start = 16.2.dp,
                            top = 19.8.dp,
                            end = 16.2.dp,
                            bottom = 19.8.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = overlayIconRes),
                        contentDescription = null,
                        tint = DduruTheme.colors.primary
                    )
                }
            }
        }
    }
}

private enum class OverlayType { PASS, ACCEPT }

@Preview(showBackground = true, backgroundColor = 0xFF1A1A2E)
@Composable
private fun AiMatchingCardPreview() {
    DduruTheme {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            AiMatchingCard(
                data = AiMatchingCardData(
                    imageUrl = null,
                    matchPercent = 92,
                    title = "Title",
                    location = "제주 서귀포",
                    dateRange = "2026.03.15 - 03.18",
                    profileImageUrl = null,
                    username = "username",
                    age = 28,
                    gender = "여성",
                    currentMembers = 3,
                    maxMembers = 4,
                    description = "제주 서쪽 해안을 따라 느긋하게 드라이브하며 숨은 카페들을 찾아가는 여행입니다. 사진 찍는 것을 좋아하시는 분이면 더 좋아요!",
                    hashtags = listOf("카페투어", "사진")
                ),
                onPass = {},
                onAccept = {},
                modifier = Modifier.size(width = 343.dp, height = 456.dp)
            )
        }
    }
}
