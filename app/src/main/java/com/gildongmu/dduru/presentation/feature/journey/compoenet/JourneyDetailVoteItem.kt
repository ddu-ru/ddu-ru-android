package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

@Composable
fun JourneyDetailVoteItem(
    title: String,
    participantCount: Int,
    isInProgress: Boolean,
    endDate: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = 16.dp,
            ),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "Q",
            style = DduruTheme.typography.Title2,
            color = DduruTheme.colors.gray900,
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray900,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${participantCount}명 참여",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "•",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = if (isInProgress) "진행중" else "종료",
                    style = DduruTheme.typography.Body2,
                    color = DduruTheme.colors.gray400,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$endDate 종료",
                style = DduruTheme.typography.Body2,
                color = DduruTheme.colors.gray400,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun JourneyDetailVoteItemPreview() {
    DduruTheme {
        JourneyDetailVoteItem(
            title = "제주도 여행 일정, 어떻게 정할까요?",
            participantCount = 8,
            isInProgress = true,
            endDate = "9월 5일",
            onClick = {},
        )
    }
}
