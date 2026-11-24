package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

private const val TICKET_SLOT_COUNT = 3

@Composable
fun SuperhostTicketSummaryCard(
    title: String,
    description: String,
    ticketCount: Int,
    modifier: Modifier = Modifier,
    onInfoClick: (() -> Unit)? = null,
    actionText: String? = null,
    onActionClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(DduruTheme.colors.gray0, RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = DduruTheme.typography.Title1.copy(lineHeight = 25.2.sp),
                        color = DduruTheme.colors.gray900
                    )

                    if (onInfoClick != null) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info_circle),
                            contentDescription = "슈퍼호스트 안내",
                            tint = DduruTheme.colors.gray400,
                            modifier = Modifier
                                .size(16.dp)
                                .noRippleClickable(onClick = onInfoClick)
                        )
                    }
                }

                Text(
                    text = description,
                    style = DduruTheme.typography.Body4,
                    color = DduruTheme.colors.gray600
                )
            }

            TicketCountRow(ticketCount = ticketCount)
        }

        if (actionText != null) {
            DduruButton(
                text = actionText,
                onClick = onActionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}

@Composable
private fun TicketCountRow(ticketCount: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            repeat(TICKET_SLOT_COUNT) { index ->
                Icon(
                    painter = painterResource(id = R.drawable.ic_superhost_ticket),
                    contentDescription = null,
                    tint = if (index < ticketCount) DduruTheme.colors.primary else DduruTheme.colors.gray200,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "${ticketCount}장",
                style = DduruTheme.typography.Title1.copy(lineHeight = 25.2.sp),
                color = DduruTheme.colors.gray700
            )
            Text(
                text = "보유중",
                style = DduruTheme.typography.Caption1.copy(lineHeight = 14.4.sp),
                color = DduruTheme.colors.gray500,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF8F9FA)
@Composable
private fun SuperhostTicketSummaryCardPreview() {
    DduruTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            SuperhostTicketSummaryCard(
                title = "슈퍼호스트 티켓",
                description = "게시글을 더 많은 동행에게 보여주세요",
                ticketCount = 3,
                onInfoClick = {},
                actionText = "슈퍼호스트 적용하기"
            )
            SuperhostTicketSummaryCard(
                title = "보유한 티켓",
                description = "사용할 수 있는 티켓이 없어요",
                ticketCount = 0
            )
        }
    }
}
