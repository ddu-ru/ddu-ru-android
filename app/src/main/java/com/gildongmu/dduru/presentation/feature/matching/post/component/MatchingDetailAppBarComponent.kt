package com.gildongmu.dduru.presentation.feature.matching.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.dduruDualShadow
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun MatchingDetailBackButton(
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
fun MatchingDetailTopActionButton(
    likeCount: Int,
    hasLiked: Boolean,
    isOwner: Boolean,
    onLikeClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMoreMenuVisible by remember { mutableStateOf(false) }
    val actionIconRes = if (isOwner) {
        R.drawable.ic_more_vertical
    } else {
        R.drawable.ic_share
    }

    val likeIconRes = if (hasLiked) {
        R.drawable.ic_heart_rounded_filled
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

            Box {
                Icon(
                    painter = painterResource(id = actionIconRes),
                    contentDescription = if (isOwner) "게시글 관리" else "공유",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable {
                            if (isOwner) {
                                isMoreMenuVisible = true
                            } else {
                                onShareClick()
                            }
                        }
                )

                if (isOwner && isMoreMenuVisible) {
                    Popup(
                        alignment = Alignment.TopEnd,
                        offset = IntOffset(0, 28),
                        onDismissRequest = { isMoreMenuVisible = false },
                        properties = PopupProperties(focusable = true)
                    ) {
                        MatchingDetailPostMoreMenu(
                            onEditClick = {
                                isMoreMenuVisible = false
                                onEditClick()
                            },
                            onDeleteClick = {
                                isMoreMenuVisible = false
                                onDeleteClick()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchingDetailPostMoreMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = modifier
            .width(120.dp)
            .height(104.dp)
            .dduruDualShadow(
                firstAlpha = 0.08f,
                secondAlpha = 0.06f,
                shape = shape
            )
            .background(DduruTheme.colors.gray0, shape)
            .border(1.dp, DduruTheme.colors.gray200, shape)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        MatchingDetailPostMoreMenuItem(
            iconRes = R.drawable.ic_pencil,
            text = "게시글 수정",
            color = DduruTheme.colors.gray700,
            onClick = onEditClick
        )
        MatchingDetailPostMoreMenuItem(
            iconRes = R.drawable.ic_trash,
            text = "게시글 삭제",
            color = DduruTheme.colors.validationRed,
            onClick = onDeleteClick
        )
    }
}

@Composable
private fun MatchingDetailPostMoreMenuItem(
    iconRes: Int,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = DduruTheme.typography.Micro,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailTopBarOwnerPreview() {
    DduruTheme {
        Row(
            modifier = Modifier.padding(20.dp).background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MatchingDetailBackButton(onClick = {})

            Spacer(modifier = Modifier.width(40.dp))

            MatchingDetailTopActionButton(
                likeCount = 12,
                hasLiked = false,
                isOwner = true,
                onLikeClick = {},
                onEditClick = {},
                onDeleteClick = {},
                onShareClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingDetailTopBarGuestPreview() {
    DduruTheme {
        Row(
            modifier = Modifier.padding(20.dp).background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MatchingDetailBackButton(onClick = {})

            Spacer(modifier = Modifier.width(40.dp))

            MatchingDetailTopActionButton(
                likeCount = 12,
                hasLiked = true,
                isOwner = false,
                onLikeClick = {},
                onEditClick = {},
                onDeleteClick = {},
                onShareClick = {}
            )
        }
    }
}
