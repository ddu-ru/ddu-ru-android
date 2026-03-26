package com.gildongmu.dduru.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun UpcomingTripCard(
    title: String,
    currentMember: Int,
    maxMember: Int,
    dDay: Int,
    taskCount: Int,
    onTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        DduruTheme.colors.secondary,
                        DduruTheme.colors.primary
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "다가오는 여행",
                        style = DduruTheme.typography.Caption1,
                        color = DduruTheme.colors.gray600
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = title,
                        style = DduruTheme.typography.Title1,
                        color = DduruTheme.colors.gray928
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99))
                        .background(DduruTheme.colors.gray0.copy(alpha = 0.3f))
                        .border(
                            width = 1.dp,
                            color = DduruTheme.colors.gray0.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(99)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "D-$dDay",
                        color = DduruTheme.colors.gray0,
                        style = DduruTheme.typography.Caption1
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TripDateRange(
                    date = "2026.03.15-03.18",
                    textColor = DduruTheme.colors.gray700
                )

                HomeMemberCount(
                    current = currentMember,
                    max = maxMember,
                    textColor = DduruTheme.colors.gray700 ,
                    textStyle= DduruTheme.typography.Caption2

                )
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = DduruTheme.colors.gray0.copy(alpha = 0.3f)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTaskClick() },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "해야 하는 업무 ${taskCount}건",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.secondary100
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = DduruTheme.colors.secondary100
                )
            }
        }
    }
}

@Preview
@Composable
private fun UpcomingTripCardPreview() {
    DduruTheme {
        UpcomingTripCard(
            title = "제주도 힐링 여행",
            currentMember = 3,
            maxMember = 4,
            dDay = 12,
            taskCount = 1,
            onTaskClick = {}
        )
    }
}
