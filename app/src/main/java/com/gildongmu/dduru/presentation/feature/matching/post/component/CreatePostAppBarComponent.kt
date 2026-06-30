package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.dduruFrostedBackground
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun CreatePostBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .dduruDualShadow(
                firstAlpha = 0.08f,
                secondAlpha = 0.10f,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                shape = CircleShape
            )
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = null,
            tint = DduruTheme.colors.black,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CreatePostTopActionButton(
    likeCount: Int,
    hasLiked: Boolean,
    isOwner: Boolean,
    onLikeClick: () -> Unit,
    onMoreClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val actionIconRes = if (isOwner) {
        R.drawable.ic_more_vertical
    } else {
        R.drawable.ic_share
    }

    val likeIconRes = if (hasLiked) {
        R.drawable.ic_heart_rounded //수정필요
    } else {
        R.drawable.ic_heart_rounded
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(
                color = DduruTheme.colors.gray0.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                shape = CircleShape
            )
            .dduruDualShadow(
                firstAlpha = 0.08f,
                secondAlpha = 0.10f,
                shape = CircleShape
            )
            .clip(CircleShape)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.noRippleClickable(onClick = onLikeClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = likeIconRes),
                    contentDescription = null,
                    tint = DduruTheme.colors.black,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = likeCount.toString(),
                    style = DduruTheme.typography.Body1,
                    color = DduruTheme.colors.gray900
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Icon(
                painter = painterResource(id = actionIconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable {
                        if (isOwner) {
                            onMoreClick()
                        } else {
                            onShareClick()
                        }
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostTopBarOwnerPreview() {
    DduruTheme {
        Row(
            modifier = Modifier.padding(20.dp).background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CreatePostBackButton(onClick = {})

            Spacer(modifier = Modifier.width(40.dp))

            CreatePostTopActionButton(
                likeCount = 12,
                hasLiked = false,
                isOwner = true,
                onLikeClick = {},
                onMoreClick = {},
                onShareClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostTopBarGuestPreview() {
    DduruTheme {
        Row(
            modifier = Modifier.padding(20.dp).background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CreatePostBackButton(onClick = {})

            Spacer(modifier = Modifier.width(40.dp))

            CreatePostTopActionButton(
                likeCount = 12,
                hasLiked = true,
                isOwner = false,
                onLikeClick = {},
                onMoreClick = {},
                onShareClick = {}
            )
        }
    }
}
