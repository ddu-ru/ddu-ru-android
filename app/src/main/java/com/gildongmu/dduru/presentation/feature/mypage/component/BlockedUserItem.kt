package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.designsystem.component.DduruButton
import com.gildongmu.dduru.core.designsystem.component.DduruButtonEnabledColors
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.journey.component.JourneyAdminMemberInfo
import com.gildongmu.dduru.presentation.feature.journey.state.JourneyAdminMember
import com.gildongmu.dduru.presentation.feature.mypage.model.BlockedUserUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BlockedUserItem(
    user: BlockedUserUiModel,
    onProfileClick: () -> Unit,
    onUnblockClick: () -> Unit,
    modifier: Modifier = Modifier,
    profileImageModel: Any? = user.profileImageUrl
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            JourneyAdminMemberInfo(
                member = JourneyAdminMember(
                    id = user.id,
                    username = user.username,
                    profileImageUrl = user.profileImageUrl,
                    roles = persistentListOf(user.introduction)
                ),
                profileImageModel = profileImageModel
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BlockedUserActionButton(
                    text = "프로필",
                    textColor = DduruTheme.colors.gray800,
                    onClick = onProfileClick
                )
                BlockedUserActionButton(
                    text = "해제",
                    textColor = DduruTheme.colors.rejectRed,
                    onClick = onUnblockClick
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray200)
    }
}

@Composable
private fun BlockedUserActionButton(
    text: String,
    textColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    DduruButton(
        text = text,
        onClick = onClick,
        enabledColors = DduruButtonEnabledColors(
            backgroundColor = DduruTheme.colors.gray0,
            textColor = textColor,
            borderColor = DduruTheme.colors.gray200
        ),
        textStyle = DduruTheme.typography.Body3,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .width(72.dp)
            .height(32.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun BlockedUserItemPreview() {
    DduruTheme {
        BlockedUserItem(
            user = BlockedUserUiModel(id = 1, username = "username", introduction = "음식 관심 많아요"),
            onProfileClick = {},
            onUnblockClick = {},
            profileImageModel = R.drawable.img_blank_profile,
            modifier = Modifier.padding(16.dp)
        )
    }
}
