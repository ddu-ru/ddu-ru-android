package com.gildongmu.dduru.presentation.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class PollOption(
    val label: String,
    val voteCount: Int,
    val isSelected: Boolean
)

@Composable
fun PollCard(
    question: String,
    options: List<PollOption>,
    onVoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalVotes = options.sumOf { it.voteCount }.coerceAtLeast(1)

    Column(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = DduruTheme.colors.black.copy(alpha = 0.12f),
                ambientColor = DduruTheme.colors.black.copy(alpha = 0.12f)
            )
            .width(220.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DduruTheme.colors.gray0,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q.",
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray958
                )
                Text(
                    text = question,
                    style = DduruTheme.typography.Title3,
                    color = DduruTheme.colors.gray958
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    PollOptionRow(
                        option = option,
                        totalVotes = totalVotes
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DduruTheme.colors.gray100,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .border(
                    width = 0.1.dp,
                    color = DduruTheme.colors.gray200,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .noRippleClickable(onVoteClick)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "투표하러 가기",
                style = DduruTheme.typography.Micro3,
                color = DduruTheme.colors.gray700
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_caretright_gray),
                contentDescription = null,
                tint = DduruTheme.colors.gray500,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
private fun PollOptionRow(
    option: PollOption,
    totalVotes: Int,
    modifier: Modifier = Modifier
) {
    val ratio = option.voteCount.toFloat() / totalVotes
    val bgColor = if (option.isSelected) DduruTheme.colors.secondary100 else DduruTheme.colors.gray100
    val borderColor = if (option.isSelected) DduruTheme.colors.secondary else DduruTheme.colors.gray200
    val fillColor = if (option.isSelected) DduruTheme.colors.secondary else DduruTheme.colors.gray200
    val textColor = if (option.isSelected) DduruTheme.colors.primary else DduruTheme.colors.gray500

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = bgColor)
            .border(width = 0.5.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = ratio)
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(fillColor)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = option.label,
                style = DduruTheme.typography.Micro3,
                color = textColor
            )
            Text(
                text = "${option.voteCount}표",
                style = DduruTheme.typography.Micro3,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PollCardPreview() {
    DduruTheme {
        PollCard(
            question = "Title",
            options = listOf(
                PollOption(label = "Subject1", voteCount = 3, isSelected = true),
                PollOption(label = "Subject2", voteCount = 1, isSelected = false)
            ),
            onVoteClick = {}
        )
    }
}
