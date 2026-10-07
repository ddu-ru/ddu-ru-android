package com.gildongmu.dduru.presentation.feature.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gildongmu.dduru.core.designsystem.theme.DduruTheme
import com.gildongmu.dduru.presentation.feature.register.profile.component.ProfileImageCircle

@Composable
fun UserInfoRow(
    nickname: String,
    greeting: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    profileImage: Any? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(17.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                ProfileImageCircle(
                    image = profileImage ?: com.gildongmu.dduru.R.drawable.img_blank_profile,
                    backgroundColor = DduruTheme.colors.gray100,
                    size = 48.dp
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = nickname,
                        style = DduruTheme.typography.Title2,
                        color = DduruTheme.colors.black
                    )
                    Text(
                        text = greeting,
                        style = DduruTheme.typography.Body4,
                        color = DduruTheme.colors.gray600
                    )
                }
            }

            StatusTagXs(
                text = "수정",
                onClick = onEditClick
            )
        }

        HorizontalDivider(thickness = 1.dp, color = DduruTheme.colors.gray100)
    }
}

@Preview(name = "UserInfoRow")
@Composable
private fun UserInfoRowPreview() {
    DduruTheme {
        UserInfoRow(
            nickname = "차한잔마시고해",
            greeting = "야호~반갑습니다~~",
            onEditClick = {}
        )
    }
}
