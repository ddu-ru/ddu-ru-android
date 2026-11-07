package com.gildongmu.dduru.presentation.feature.mypage.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.mypage.component.MyPostCompanionTag
import com.gildongmu.dduru.presentation.feature.mypage.component.MyPostGenderTag
import com.gildongmu.dduru.presentation.feature.mypage.component.PostThumbnail
import com.gildongmu.dduru.presentation.feature.mypage.component.PostTitleAndMeta
import com.gildongmu.dduru.presentation.feature.mypage.model.SuperhostPostUiModel

@Composable
internal fun SuperhostSheetPostSummary(post: SuperhostPostUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            PostTitleAndMeta(
                title = post.title,
                destination = post.destination,
                dateRange = post.dateRange,
                capacity = post.capacity
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                post.gender?.let { MyPostGenderTag(gender = it) }
                post.companionType?.let { MyPostCompanionTag(companionType = it) }
            }
        }

        PostThumbnail(thumbnailUrl = post.thumbnailUrl)
    }
}

@Composable
internal fun SuperhostSheetDetailRow(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )
        Text(
            text = value,
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )
    }
}

@Composable
internal fun SuperhostSheetTicketRow(ticketCount: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "보유 티켓",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray600
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SheetTicketCount(
                count = ticketCount,
                modifier = Modifier.alpha(0.6f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = DduruTheme.colors.black,
                modifier = Modifier
                    .size(12.dp)
                    .padding(horizontal = 1.dp)
                    .alpha(0.6f)
            )
            SheetTicketCount(count = (ticketCount - 1).coerceAtLeast(0))
        }
    }
}

@Composable
private fun SheetTicketCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_superhost_ticket),
            contentDescription = null,
            tint = DduruTheme.colors.primary,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = "${count}장",
            style = DduruTheme.typography.Body2,
            color = DduruTheme.colors.gray800
        )
    }
}
