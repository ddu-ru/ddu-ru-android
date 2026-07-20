package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun ParticipationCard(
    username: String,
    postTitle: String,
    time: String,
    statusLabel: String,
    onReject: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = DduruTheme.colors.gray200)
            .padding(top = 20.dp, bottom = 20.dp)
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
                        style = DduruTheme.typography.Title1,
                        color = DduruTheme.colors.gray800
                    )
                    ParticipationStatusBadge(
                        label = statusLabel,
                        backgroundColor = DduruTheme.colors.secondary,
                        textColor = DduruTheme.colors.primary
                    )
                }
                Text(
                    text = postTitle,
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray500
                )
            }
            Text(
                text = time,
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray600
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .semantics { contentDescription = "거절" }
                    .noRippleClickable(onClick = onReject)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .border(
                            width = 1.dp,
                            color = DduruTheme.colors.gray300,
                            shape = RoundedCornerShape(9.dp)
                        )
                        .background(
                            color = DduruTheme.colors.gray100,
                            shape = RoundedCornerShape(9.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = DduruTheme.colors.gray700
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .semantics { contentDescription = "수락" }
                    .noRippleClickable(onClick = onAccept)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = DduruTheme.colors.primary,
                            shape = RoundedCornerShape(9.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = DduruTheme.colors.gray0
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ParticipationCardPreview() {
    DduruTheme {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ParticipationCard(
                username = "username",
                postTitle = "제주 애월 2박 3일",
                time = "방금",
                statusLabel = "연락중",
                onReject = {},
                onAccept = {}
            )
        }
    }
}
