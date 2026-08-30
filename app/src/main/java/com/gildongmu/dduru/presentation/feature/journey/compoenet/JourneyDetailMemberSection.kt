package com.gildongmu.dduru.presentation.feature.journey.compoenet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.extension.noRippleClickable
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme

data class JourneyMemberUiState(
    val id: Long,
    val username: String,
    val profileImageUrl: String?,
    val role: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JourneyDetailMemberSection(
    members: List<JourneyMemberUiState>,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "멤버 현황",
                style = DduruTheme.typography.Title2,
                color = DduruTheme.colors.gray800
            )

            Row(
                modifier = Modifier.noRippleClickable(onClick = onManageClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "멤버 관리",
                    style = DduruTheme.typography.Caption1,
                    color = DduruTheme.colors.gray600
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = DduruTheme.colors.gray600,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val itemWidth = maxWidth * 0.2f - 1.dp

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 5,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                members.forEach { member ->
                    JourneyMemberItem(
                        member = member,
                        modifier = Modifier.width(itemWidth)
                    )
                }
            }
        }
    }
}

@Composable
private fun JourneyMemberItem(
    member: JourneyMemberUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = member.profileImageUrl ?: R.drawable.img_blank_profile,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = member.username,
            style = DduruTheme.typography.Micro2,
            color = DduruTheme.colors.black,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = member.role,
            style = DduruTheme.typography.Micro3,
            color = DduruTheme.colors.gray400,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JourneyDetailMemberSectionPreview() {
    DduruTheme {
        JourneyDetailMemberSection(
            members = listOf(
                JourneyMemberUiState(1, "둘루", null, "방장"),
                JourneyMemberUiState(2, "여행가자", null, "멤버"),
                JourneyMemberUiState(3, "제주러버", null, "멤버"),
                JourneyMemberUiState(4, "민지", null, "멤버"),
                JourneyMemberUiState(5, "준호", null, "멤버"),
                JourneyMemberUiState(6, "수연", null, "멤버")
            ),
            onManageClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
