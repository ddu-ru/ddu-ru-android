package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

enum class ApplicationCardAction {
    CANCEL,
    ENTER,
    NONE
}

@Composable
fun ApplicationCard(
    username: String,
    postTitle: String,
    time: String,
    statusLabel: String,
    statusBgColor: Color,
    statusTextColor: Color,
    action: ApplicationCardAction,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(DduruTheme.colors.gray100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_people),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = DduruTheme.colors.gray400
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = username,
                                modifier = Modifier.weight(1f, fill = false),
                                style = DduruTheme.typography.Title1,
                                color = DduruTheme.colors.gray800,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            ParticipationStatusBadge(
                                label = statusLabel,
                                backgroundColor = statusBgColor,
                                textColor = statusTextColor
                            )
                        }
                        Text(
                            text = postTitle,
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.gray500,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = time,
                        style = DduruTheme.typography.Body2,
                        color = DduruTheme.colors.gray600
                    )
                }
            }

            when (action) {
                ApplicationCardAction.CANCEL -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(32.dp)
                            .border(
                                width = 1.dp,
                                color = DduruTheme.colors.rejectRedBg,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable(onClick = onActionClick)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "신청 취소",
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.rejectRed
                        )
                    }
                }
                ApplicationCardAction.ENTER -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(32.dp)
                            .background(
                                color = DduruTheme.colors.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable(onClick = onActionClick)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "팀채팅",
                            style = DduruTheme.typography.Title3,
                            color = DduruTheme.colors.gray0
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_caret_right_white),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = DduruTheme.colors.gray0
                        )
                    }
                }
                ApplicationCardAction.NONE -> Unit
            }
        }
        HorizontalDivider(
            color = DduruTheme.colors.gray200,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicationCardCancelPreview() {
    DduruTheme {
        ApplicationCard(
            username = "username",
            postTitle = "제주 애월 2박 3일",
            time = "방금",
            statusLabel = "연락중",
            statusBgColor = DduruTheme.colors.secondary,
            statusTextColor = DduruTheme.colors.primary,
            action = ApplicationCardAction.CANCEL,
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicationCardEnterPreview() {
    DduruTheme {
        ApplicationCard(
            username = "username",
            postTitle = "제주 애월 2박 3일",
            time = "방금",
            statusLabel = "수락됨",
            statusBgColor = DduruTheme.colors.acceptGreenBg,
            statusTextColor = DduruTheme.colors.acceptGreen,
            action = ApplicationCardAction.ENTER,
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApplicationCardNonePreview() {
    DduruTheme {
        ApplicationCard(
            username = "username",
            postTitle = "제주 애월 2박 3일",
            time = "방금",
            statusLabel = "거절됨",
            statusBgColor = DduruTheme.colors.rejectRedBg,
            statusTextColor = DduruTheme.colors.rejectRed,
            action = ApplicationCardAction.NONE,
            onActionClick = {}
        )
    }
}
